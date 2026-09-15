package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.dto.LoginRequestDTO;
import com.Huellitas.Hotel.dto.LoginResponseDTO;
import com.Huellitas.Hotel.exception.CredencialesInvalidasException;
import com.Huellitas.Hotel.model.Usuario;
import com.Huellitas.Hotel.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServicio jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtServicio jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO iniciarSesion(LoginRequestDTO datos) {

        Usuario usuario = usuarioRepository
                .findByEmail(datos.email())
                .orElseThrow(() ->
                        new CredencialesInvalidasException(
                                "Usuario o contraseña incorrectos"
                        )
                );

        if (!passwordEncoder.matches(
                datos.contrasena(),
                usuario.getContrasena()
        )) {

            throw new CredencialesInvalidasException(
                    "Usuario o contraseña incorrectos"
            );
        }

        String token = jwtService.generarToken(usuario);

        return new LoginResponseDTO(
                token,
                usuario.getEmail(),
                usuario.getRol()
        );
    }
}