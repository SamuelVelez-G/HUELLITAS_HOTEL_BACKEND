package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.dto.*;
import com.Huellitas.Hotel.model.Especie;
import com.Huellitas.Hotel.model.Mascota;
import com.Huellitas.Hotel.model.Usuario;
import com.Huellitas.Hotel.repository.EspecieRepository;
import com.Huellitas.Hotel.repository.MascotaRepository;
import com.Huellitas.Hotel.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final EspecieRepository especieRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<MascotaResponseDTO> listarTodas() {
        return mascotaRepository.findAll().stream()
                .map(this::aResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public MascotaResponseDTO obtenerPorId(Long id) {
        return mascotaRepository.findById(id)
                .map(this::aResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada con ID: " + id));
    }

    @Transactional
    public MascotaResponseDTO crear(MascotaRequestDTO dto) {
        Especie especie = especieRepository.findById(dto.especieId())
                .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada con ID: " + dto.especieId()));

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + dto.usuarioId()));

        Mascota mascota = new Mascota();
        mascota.setNombre(dto.nombre());
        mascota.setRaza(dto.raza());
        mascota.setEdad(dto.edad());
        mascota.setEspecie(especie);
        mascota.setUsuario(usuario);

        return aResponseDTO(mascotaRepository.save(mascota));
    }

    @Transactional
    public MascotaResponseDTO actualizar(Long id, MascotaRequestDTO dto) {
        Mascota existente = mascotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada con ID: " + id));

        Especie especie = especieRepository.findById(dto.especieId())
                .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada con ID: " + dto.especieId()));

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + dto.usuarioId()));

        existente.setNombre(dto.nombre());
        existente.setRaza(dto.raza());
        existente.setEdad(dto.edad());
        existente.setEspecie(especie);
        existente.setUsuario(usuario);

        return aResponseDTO(mascotaRepository.save(existente));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!mascotaRepository.existsById(id)) {
            throw new EntityNotFoundException("Mascota no encontrada con ID: " + id);
        }
        mascotaRepository.deleteById(id);
    }

    private MascotaResponseDTO aResponseDTO(Mascota mascota) {
        return new MascotaResponseDTO(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getRaza(),
                mascota.getEdad(),
                new EspecieResumenDTO(
                        mascota.getEspecie().getId(),
                        mascota.getEspecie().getNombre()
                ),
                new UsuarioResumenDTO(
                        mascota.getUsuario().getId(),
                        mascota.getUsuario().getNombre()
                )
        );
    }
}