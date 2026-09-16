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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                // Habilitar la configuración de CORS vinculada al Bean
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))


                // API REST stateless: desactivamos CSRF
                .csrf(csrf -> csrf.disable())

                // Manejo de sesión mediante JWT sin estado
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // 1. ENDPOINTS PÚBLICOS (Sin autenticación)
                        // =========================
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/especies", "/api/especies/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/servicios", "/api/servicios/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // =========================
                        // 2. ENDPOINTS EXCLUSIVOS DE ADMINISTRADOR
                        // =========================
                        .requestMatchers("/api/usuarios/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/especies/**", "/api/servicios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/especies/**", "/api/servicios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")


                        // =========================
                        // 3. ENDPOINTS AUTENTICADOS (USER Y ADMIN)
                        // =========================
                        // Permite a usuarios logueados gestionar sus mascotas, reservas y perfil
                        .requestMatchers("/api/usuarios/**", "/api/mascotas/**", "/api/reservas/**").authenticated()

                        // Cualquier otra ruta requiere estar autenticado
                        .anyRequest().authenticated()
                )

                // Filtro para validar el Token JWT antes del filtro de usuario/contraseña
                .addFilterBefore(filtroJwt, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager administradorAutenticacion(AuthenticationConfiguration configuracion) throws Exception {
        return configuracion.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder codificadorContrasena() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Lista de orígenes permitidos (Frontend local + el enlace de GitHub Pages)
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:5173",
                "http://localhost:4200",
                "http://127.0.0.1:5173",
                "https://samuelvelez-g.github.io"
        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}