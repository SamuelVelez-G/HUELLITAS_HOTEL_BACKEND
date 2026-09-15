package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.model.Usuario;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServicio {

    @Value("${jwt.secret}")
    private String claveSecreta;

    @Value("${jwt.expiration}")
    private long tiempoExpiracion;

    /**
     * Genera un token utilizando el correo.
     */
    public String generarToken(String correo) {

        Date ahora = new Date();

        Date expiracion =
                new Date(ahora.getTime() + tiempoExpiracion);

        return Jwts.builder()
                .subject(correo)
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(
                        generarClaveSecreta(),
                        Jwts.SIG.HS256
                )
                .compact();
    }

    /**
     * Genera un token utilizando directamente el usuario.
     */
    public String generarToken(Usuario usuario) {

        return generarToken(usuario.getEmail());
    }

    /**
     * Obtiene el correo almacenado dentro del token.
     */
    public String obtenerCorreoDesdeToken(String token) {

        return Jwts.parser()
                .verifyWith(generarClaveSecreta())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Comprueba si el token es válido.
     */
    public boolean esTokenValido(String token) {

        try {

            obtenerCorreoDesdeToken(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {

            return false;
        }
    }

    /**
     * Genera la clave utilizada para firmar y verificar los JWT.
     */
    private SecretKey generarClaveSecreta() {

        return Keys.hmacShaKeyFor(
                claveSecreta.getBytes(StandardCharsets.UTF_8)
        );
    }
}