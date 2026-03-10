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

                // rutas públicas
                .requestMatchers("/login","/registro","/css/**").permitAll()

                // SOLO ADMIN puede acceder a empleados
                .requestMatchers("/empleados/**").hasRole("ADMIN")

                // cualquier otra página requiere login
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