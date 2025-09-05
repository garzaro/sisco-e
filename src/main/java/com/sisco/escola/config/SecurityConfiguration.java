package com.sisco.escola.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;


/**
 * To-do
 * [] - precisa definir role ("USER")
 * [] - definir redirecionamento logout
 * **/

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        String[] acessoPublico = {"/", "/register", "/login", "/logout"};

        http
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                        .contentSecurityPolicy(csp -> csp
                        .policyDirectives("default-src 'self'")
                )
        )
                .securityMatcher("/login")
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                )
                .authorizeHttpRequests(autorizacao -> {
                    autorizacao
                            .requestMatchers(acessoPublico).permitAll()
                            .requestMatchers("/home").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(formulario -> formulario
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/public")
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults()
                );

        return http.build();
    }
}
