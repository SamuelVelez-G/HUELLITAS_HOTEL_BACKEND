package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.dto.MascotaResumenDTO;
import com.Huellitas.Hotel.dto.UsuarioRequestDTO;
import com.Huellitas.Hotel.dto.UsuarioResponseDTO;
import com.Huellitas.Hotel.model.Rol;
import com.Huellitas.Hotel.model.Usuario;
import com.Huellitas.Hotel.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    public final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //CRUD

    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO datos){
        if(usuarioRepository.existsByEmail(datos.email())){
            throw new IllegalArgumentException("El email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(datos.nombre());
        usuario.setTelefono(datos.telefono());
        usuario.setEmail(datos.email());
        usuario.setContrasena(datos.contrasena());
        usuario.setRol(Rol.USER);
        usuario.setFechaRegistro(LocalDateTime.now());

        Usuario creado = usuarioRepository.save(usuario);
        return mapearAUsuarioResponseDTO(creado);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarUsuarios(){
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapearAUsuarioResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDTO> buscarUsuarioId(Long id){
        return usuarioRepository.findById(id)
                .map(this::mapearAUsuarioResponseDTO);
    }

    @Transactional
    public Optional<UsuarioResponseDTO> actualizarUsuario(Long id, UsuarioRequestDTO datos){
        return usuarioRepository.findById(id)
                .map(user -> {
                    user.setNombre(datos.nombre());
                    user.setTelefono(datos.telefono());
                    user.setEmail(datos.email());
                    //user.setContrasena(datos.contrasena()); //Revisar si se deja de esta forma la actualizacion de contrasena

                    Usuario actualizado = usuarioRepository.save(user);
                    return mapearAUsuarioResponseDTO(actualizado);
                });
    }

    @Transactional
    public boolean eliminarUsuario(Long id){
        if(!usuarioRepository.existsById(id)){
            return false;
        }
        usuarioRepository.deleteById(id);
        return true;
    }


    //MAPEO

    //FALTA VALIDAR CAMPOS DE MASCOTA RESUMEN DTO
    private UsuarioResponseDTO mapearAUsuarioResponseDTO(Usuario usuario){
        List<MascotaResumenDTO> mascotaResumen = usuario.getMascotas()
                .stream()
                .map(mascota -> new MascotaResumenDTO(
                        mascota.getId(),
                        mascota.getNombre(),
                        mascota.getEspecie().getNombre()
                ))
                .toList();
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getTelefono(),
                usuario.getEmail(),
                mascotaResumen
        );
    }
}
