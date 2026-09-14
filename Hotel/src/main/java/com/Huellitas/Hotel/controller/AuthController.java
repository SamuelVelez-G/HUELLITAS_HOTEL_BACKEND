package com.Huellitas.Hotel.controller;

import com.Huellitas.Hotel.dto.LoginRequestDTO;
import com.Huellitas.Hotel.dto.LoginResponseDTO;
import com.Huellitas.Hotel.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return authService.iniciarSesion(dto);
    }
}