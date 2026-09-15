package com.Huellitas.Hotel.config;

import com.Huellitas.Hotel.service.JwtServicio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroJwt extends OncePerRequestFilter {

    private final JwtServicio jwtServicio;
    private final UserDetailsService servicioDetallesUsuario;

    public FiltroJwt(
            JwtServicio jwtServicio,
            UserDetailsService servicioDetallesUsuario
    ) {
        this.jwtServicio = jwtServicio;
        this.servicioDetallesUsuario = servicioDetallesUsuario;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest solicitud,
            HttpServletResponse respuesta,
            FilterChain filtro
    ) throws ServletException, IOException {

        String cabecera = solicitud.getHeader("Authorization");

        // Si no existe token, dejamos continuar la petición.
        // SecurityConfig decidirá posteriormente si el endpoint
        // es público o requiere autenticación.
        if (cabecera == null || !cabecera.startsWith("Bearer ")) {
            filtro.doFilter(solicitud, respuesta);
            return;
        }

        String token = cabecera.substring(7);

        // Si el token es inválido, simplemente continuamos.
        if (!jwtServicio.esTokenValido(token)) {
            filtro.doFilter(solicitud, respuesta);
            return;
        }

        String correo = jwtServicio.obtenerCorreoDesdeToken(token);

        // Evitamos sobrescribir una autenticación existente.
        if (correo != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails usuario =
                    servicioDetallesUsuario.loadUserByUsername(correo);

            UsernamePasswordAuthenticationToken autenticacion =
                    new UsernamePasswordAuthenticationToken(
                            usuario,
                            null,
                            usuario.getAuthorities()
                    );

            autenticacion.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(solicitud)
            );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(autenticacion);
        }

        filtro.doFilter(solicitud, respuesta);
    }
}