package com.Huellitas.Hotel.controller;

import com.Huellitas.Hotel.dto.LoginRequestDTO;
import com.Huellitas.Hotel.dto.TokenRespuestaDTO;
import com.Huellitas.Hotel.service.JwtServicio;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager administradorAutenticacion;
    private final JwtServicio jwtServicio;

    public AuthController(AuthenticationManager administradorAutenticacion, JwtServicio jwtServicio) {
        this.administradorAutenticacion = administradorAutenticacion;
        this.jwtServicio = jwtServicio;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenRespuestaDTO> iniciarSesion(@RequestBody @Valid LoginRequestDTO datos) {
        Authentication autenticacion = administradorAutenticacion.authenticate(
                new UsernamePasswordAuthenticationToken(datos.email(), datos.contrasena())
        );

        String token = jwtServicio.generarToken(autenticacion.getName());
        return ResponseEntity.ok(new TokenRespuestaDTO(token));
    }
}