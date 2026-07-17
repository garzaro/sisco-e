package com.sisco.escola.config;

import com.sisco.escola.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Configuração principal do ecossistema de segurança (Spring Security 6).
 * 
 * Centraliza o controle de acesso de rotas, injeção de filtros customizados, 
 * tratamento REST de exceções de segurança e política de CORS.
 * Define a cadeia de filtros de segurança da aplicação.
 * Configura:
 * Suporte a CORS acoplado ao Spring Security.
 * CSRF desabilitado (seguro para APIs REST stateless).
 * Regras de autorização de requisições:
 * POST em "/api/usuario" (Cadastro) -> Liberado
 * POST em "/api/usuario/autenticar" (Login) -> Liberado
 * Qualquer outra requisição -> Exige autenticação
 * Política de sessão stateless (sem armazenamento de sessão em servidor).
 * Tratamento centralizado de erros de segurança (401 e 403) via AuthExceptionHandler.
 * Inserção do JwtAuthenticationFilter antes da autenticação padrão por senha do Spring.
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig {

   private final JwtAuthenticationFilter jwtAuthenticationFilter;
//   private final UserDetailsService userDetailsService;
   private final UsuarioDetailsService usuarioDetailsService;

   public SecurityConfig(
           JwtAuthenticationFilter jwtFilter,
           UsuarioDetailsService usuarioDetailsService
   ) {
    	super();
        this.usuarioDetailsService = usuarioDetailsService;
    	this.jwtAuthenticationFilter = jwtFilter;
//        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
//       return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8(); // factory method
       return new Argon2PasswordEncoder(16, 32, 1, 65536, 3);
   }

   /**
    * O SecurityFilterChain protege rotas e valida tokens que já existem (via JwtAuthenticationFilter).
    * Ele não emite o primeiro token — não existe endpoint nenhum que receba email/senha e devolva um JWT.
    * **/
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
           .csrf(csrf -> csrf.disable()) // stateless + JWT: CSRF não se aplica
           .cors(cors -> cors.configurationSource(corsConfigurationSource()))
           .sessionManagement(session ->
                   session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           .authorizeHttpRequests(auth -> auth
                   .requestMatchers(HttpMethod.POST, "/api/auth/sign-in/", "/api/user/sign-up/").permitAll()
                   .anyRequest().authenticated()
           )
           .authenticationProvider(authenticationProvider())
           /**garante que o filtro JWT rode antes do filtro padrão de autenticação por formulário do Spring Security,**/
           .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
       return http.build();
    }
   
   /**esse cara é o recepcionista que obedece o porteiro, ele verifica se a senha existe na base e se são iguais**/
   @Bean
   public AuthenticationProvider authenticationProvider() {
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setUserDetailsService(usuarioDetailsService);
       provider.setPasswordEncoder(passwordEncoder());
       return provider;
   }

   /**Esse cara é o porteiro do predio, ele pega o objeto, o cracha, e pede ao provider para verificar la na base**/
   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
       return config.getAuthenticationManager();
   }

   /**
    *
    * Provedor dinâmico de configuração de CORS baseado em propriedades externalizadas.
    * Max Age = 3600 segundos (1 hora): Cacheia a requisição de pre-flight do CORS diminuindo latência.
    * Arrays.asList - mutavel - maior sobrecarga e aceita nulo
    * List.of - imutavel
    */
   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
       CorsConfiguration configuration = new CorsConfiguration();
       configuration.setAllowedOrigins(List.of("http://localhost:3000"));
       configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
       configuration.setAllowedHeaders(List.of("Authorization, Content-Type")); /**"Accept"**/
       configuration.setAllowCredentials(true);
       configuration.setMaxAge(3600L);
       /**
        *  Atua como um roteador de configurações de CORS, decidindo quais origens,
        *  metodos e cabecalhos sao permitidos dependendo da URL acessada
        */
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", configuration);
       return source;
   }
}
