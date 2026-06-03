package com.sisco.escola.config.security;

import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
 * Implementa a arquitetura moderna baseada em Beans de configuração, dispensando o antigo WebSecurityConfigurerAdapter.
 * Centraliza o controle de acesso de rotas, injeção de filtros customizados, tratamento REST de exceções de segurança,
 * beans de encriptação robusta com Argon2id e política de CORS.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthExceptionHandler authExceptionHandler;

    @Value("${app.security.cors.allowed-origins}")
    private String allowedOrigins;

    /**
     * Define a cadeia de filtros de segurança da aplicação.
     * 
     * Configura:
     * - Suporte a CORS acoplado ao Spring Security.
     * - CSRF desabilitado (seguro para APIs REST stateless).
     * - Regras de autorização de requisições:
     *   • POST em "/api/usuario" (Cadastro) -> Liberado
     *   • POST em "/api/usuario/autenticar" (Login) -> Liberado
     *   • Qualquer outra requisição -> Exige autenticação
     * - Política de sessão stateless (sem armazenamento de sessão em servidor).
     * - Tratamento centralizado de erros de segurança (401 e 403) via AuthExceptionHandler.
     * - Inserção do JwtAuthenticationFilter antes da autenticação padrão por senha do Spring.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/usuario").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/usuario/autenticar").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(authExceptionHandler)
                .accessDeniedHandler(authExceptionHandler)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Bean responsável pela criptografia segura de senhas usando o algoritmo Argon2id.
     * 
     * Os parâmetros foram configurados de forma explícita de acordo com as especificações exigidas:
     * - saltLength = 16 bytes: Garante alta entropia contra ataques de dicionário e tabelas Rainbow.
     * - hashLength = 32 bytes: Tamanho robusto para o hash final da senha.
     * - parallelism = 1 thread: Limita o paralelismo de execução de threads da CPU por verificação de hash.
     * - memory = 65536 KB (64 MB): Custo de memória que protege contra ataques acelerados por hardware (ASIC/GPU).
     * - iterations = 3: Custo computacional que define o tempo de processamento ideal.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        int saltLength = 16;
        int hashLength = 32;
        int parallelism = 1;
        int memory = 65536; 
        int iterations = 3;

        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }

    /**
     * Provedor dinâmico de configuração de CORS baseado em propriedades externalizadas.
     * 
     * Habilita origens cruzadas seguras em conformidade com os requisitos técnicos:
     * - Origens configuradas dinamicamente via propriedades (facilitando troca entre Dev e Prod).
     * - Métodos HTTP aceitos: GET, POST, PUT, DELETE, OPTIONS.
     * - Headers permitidos: Authorization, Content-Type, Accept.
     * - Permitir credenciais: true (necessário para persistência de cookies ou autenticação Bearer integrada).
     * - Max Age = 3600 segundos (1 hora): Cacheia a requisição de pre-flight do CORS diminuindo latência.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Converte a string de origens separadas por vírgula em uma lista
        List<String> origins = Arrays.asList(allowedOrigins.split(","));
        configuration.setAllowedOrigins(origins);
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Implementação integrada de {@link UserDetailsService} no Spring Security.
     * 
     * Busca o usuário no banco de dados tanto por e-mail quanto por nome de usuário para flexibilidade no login,
     * e retorna a representação {@link org.springframework.security.core.userdetails.User} com suas respectivas Roles/Authorities.
     */
    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return username -> {
            Usuario usuario = usuarioRepository.findByEmail(username)
                    .or(() -> usuarioRepository.findByUsuario(username))
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado com e-mail ou username: " + username));
            
            return org.springframework.security.core.userdetails.User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getSenha())
                    .disabled(!Boolean.TRUE.equals(usuario.getAtivo()))
                    .authorities("ROLE_USER") // Permissão padrão base
                    .build();
        };
    }

    /**
     * Gerenciador padrão de autenticação do Spring Security.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
