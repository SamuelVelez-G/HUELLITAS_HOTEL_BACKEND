package com.Huellitas.Hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfiguracionSeguridad {

    private final FiltroJwt filtroJwt;

    public ConfiguracionSeguridad(FiltroJwt filtroJwt) {
        this.filtroJwt = filtroJwt;
    }

    @Bean
    public SecurityFilterChain filtroSeguridad(HttpSecurity http) throws Exception {

        http
                // API REST: no utilizamos CSRF
                .csrf(csrf -> csrf.disable())

                // La autenticación se maneja mediante JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // ENDPOINTS PÚBLICOS
                        // =========================

                        // Login
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/login"
                        ).permitAll()

                        // Registro de usuarios
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/usuarios"
                        ).permitAll()

                        // Consultar especies
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/especies",
                                "/api/especies/**"
                        ).permitAll()

                        // =========================
                        // USUARIOS
                        // =========================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/usuarios"
                        ).hasAnyRole("ADMIN", "USER")

                        // =========================
                        // ADMINISTRACIÓN
                        // =========================

                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")

                        // Crear veterinarios y especialidades
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/veterinarios",
                                "/api/especialidades"
                        ).hasRole("ADMIN")

                        // =========================
                        // CITAS
                        // =========================

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/citas/*/atender"
                        ).hasAnyRole("VETERINARIO", "ADMIN")

                        // =========================
                        // DUEÑOS, MASCOTAS Y CITAS
                        // =========================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/duenos",
                                "/api/mascotas",
                                "/api/citas"
                        ).hasAnyRole("RECEPCIONISTA", "ADMIN")

                        // =========================
                        // ELIMINACIONES
                        // =========================

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/**"
                        ).hasRole("ADMIN")

                        // =========================
                        // ERROR
                        // =========================

                        .requestMatchers("/error").permitAll()

                        // =========================
                        // TODO LO DEMÁS
                        // =========================

                        .anyRequest().authenticated()
                )

                // Filtro JWT antes del filtro de autenticación
                .addFilterBefore(
                        filtroJwt,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager administradorAutenticacion(
            AuthenticationConfiguration configuracion
    ) throws Exception {

        return configuracion.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder codificadorContrasena() {
        return new BCryptPasswordEncoder();
    }
}