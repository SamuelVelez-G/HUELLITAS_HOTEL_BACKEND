package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.exception.UsuarioDuplicadoException;
import com.Huellitas.Hotel.model.RolUsuario;
import com.Huellitas.Hotel.model.Usuario;
import com.Huellitas.Hotel.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Mónica (Registro de cuentas) o Juan (DTO) llaman este método desde
    // el controlador de registro, pasando los datos ya validados.
    @Transactional
    public Usuario registrarUsuario(String nombre, String telefono, String email,
                                    String contrasenaPlano, RolUsuario rol) {

        if (usuarioRepository.existsByEmail(email)) {
            throw new UsuarioDuplicadoException("Ya existe una cuenta registrada con el correo: " + email);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setEmail(email);
        usuario.setContrasena(passwordEncoder.encode(contrasenaPlano)); // nunca se guarda en texto plano
        usuario.setRol(rol);
        usuario.setFechaRegistro(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }


    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Optional<Usuario> cambiarRol(Long id, RolUsuario nuevoRol) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setRol(nuevoRol);
            return usuarioRepository.save(usuario);
        });
    }
}