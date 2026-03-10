package com.proyecto.controlempleados.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
            .authorizeHttpRequests(auth -> auth

                    // Archivos estáticos permitidos para todos
                    .requestMatchers("/css/**","/js/**","/images/**").permitAll()

                    // Páginas de login y registro 
                    .requestMatchers("/login","/registro").permitAll()

                    // Solo administrador puede acceder a rutas bajo /admin/**
                    .requestMatchers("/admin/**").hasRole("ADMIN")

                    // TODAS las demás requieren autenticación
                    .anyRequest().authenticated()
            )
            .formLogin(login -> login
                    .loginPage("/login")
                    .defaultSuccessUrl("/home", true)
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutSuccessUrl("/login")
                    .permitAll()
            );

        return http.build();
    }
}