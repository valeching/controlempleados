package com.proyecto.controlempleados.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//aca se conf la seguridad de la app
@Configuration
public class SecurityConfig {

    // aqui se agregan las configuraciones de seguridad, como las rutas públicas, privadas, roles, etc
    @Bean 
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
            .authorizeHttpRequests(auth -> auth

               
                .requestMatchers("/login","/registro","/css/**").permitAll()

                .requestMatchers("/empleados/**").hasRole("ADMIN")

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