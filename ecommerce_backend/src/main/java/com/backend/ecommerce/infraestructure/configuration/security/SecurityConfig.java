package com.backend.ecommerce.infraestructure.configuration.security;

import com.backend.ecommerce.infraestructure.configuration.security.jwt.JWTFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

//Anotacion que rompe los filtros por defecto de spring security
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security)throws Exception { //Parametro que viene en el header de la peticion
        security.cors(cors -> cors.configurationSource(
                request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    //Permitir peticiones a cualquier sitio, mediante cualquier metodo HTTP y que permita los headers en cada peticion
                    configuration.setAllowedOrigins(List.of("*"));
                    configuration.setAllowedMethods(List.of("*"));
                    configuration.setAllowedHeaders(List.of("*"));
                    return configuration;
                }
        ));

        security.csrf(AbstractHttpConfigurer::disable)
                //Caputra todas las peticiones y le coloca filtros
                .authorizeHttpRequests(auth -> auth.requestMatchers("/admin/categories/**").hasRole("ADMIN")
                        .requestMatchers("/admin/products/**").hasRole("ADMIN")
                        .requestMatchers("/orders/**").authenticated()
                        .requestMatchers("/payments/success").permitAll()
                        .requestMatchers("/payments/canceled").permitAll()
                        .requestMatchers("/payments/**").authenticated()
                        .requestMatchers("/home/**").permitAll()
                        .requestMatchers("/security/user/**").permitAll() //Permite todo acceso sin autenticacion a esta endpoint
                        .requestMatchers("images/**").permitAll()
                        .anyRequest().authenticated()) //Toda otra endpoint necesitara de autenticacion
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint((request, response, authException) ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Credenciales incorrectas")));

        return security.build();
    }
    //Creacion de una clase para encriptar
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
