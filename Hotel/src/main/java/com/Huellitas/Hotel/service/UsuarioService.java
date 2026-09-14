package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.dto.MascotaResumenDTO;
import com.Huellitas.Hotel.dto.UsuarioRequestDTO;
import com.Huellitas.Hotel.dto.UsuarioResponseDTO;
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

    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO datos) {
        if (usuarioRepository.existsByEmail(datos.email())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(datos.nombre());
        usuario.setTelefono(datos.telefono());
        usuario.setEmail(datos.email());
        usuario.setContrasena(passwordEncoder.encode(datos.contrasena()));
        usuario.setRol(RolUsuario.CLIENTE);
        usuario.setFechaRegistro(LocalDateTime.now());

        Usuario creado = usuarioRepository.save(usuario);
        return mapearAUsuarioResponseDTO(creado);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapearAUsuarioResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDTO> buscarUsuarioId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::mapearAUsuarioResponseDTO);
    }

    @Transactional
    public Optional<UsuarioResponseDTO> actualizarUsuario(Long id, UsuarioRequestDTO datos) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    if (!usuario.getEmail().equalsIgnoreCase(datos.email())
                            && usuarioRepository.existsByEmail(datos.email())) {
                        throw new IllegalArgumentException("El email ya está registrado");
                    }

                    usuario.setNombre(datos.nombre());
                    usuario.setTelefono(datos.telefono());
                    usuario.setEmail(datos.email());

                    Usuario actualizado = usuarioRepository.save(usuario);
                    return mapearAUsuarioResponseDTO(actualizado);
                });
    }

    @Transactional
    public boolean eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            return false;
        }
        usuarioRepository.deleteById(id);
        return true;
    }

    private UsuarioResponseDTO mapearAUsuarioResponseDTO(Usuario usuario) {
        List<MascotaResumenDTO> mascotas = usuario.getMascotas() == null
                ? List.of()
                : usuario.getMascotas().stream()
                    .map(mascota -> new MascotaResumenDTO(mascota.getId(), mascota.getNombre()))
                    .toList();

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getTelefono(),
                usuario.getEmail(),
                mascotas
        );
    }
}