package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.dto.EspecieResumenDTO;
import com.Huellitas.Hotel.dto.MascotaRequestDTO;
import com.Huellitas.Hotel.dto.MascotaResponseDTO;
import com.Huellitas.Hotel.model.Especie;
import com.Huellitas.Hotel.model.Mascota;
import com.Huellitas.Hotel.repository.EspecieRepository;
import com.Huellitas.Hotel.repository.MascotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final EspecieRepository especieRepository;

    public MascotaService(MascotaRepository mascotaRepository, EspecieRepository especieRepository) {
        this.mascotaRepository = mascotaRepository;
        this.especieRepository = especieRepository;
    }

    @Transactional(readOnly = true)
    public List<MascotaResponseDTO> listarTodas() {
        return mascotaRepository.findAll().stream()
                .map(this::aResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<MascotaResponseDTO> obtenerPorId(Long id) {
        return mascotaRepository.findById(id).map(this::aResponseDTO);
    }

    @Transactional
    public Optional<MascotaResponseDTO> crear(MascotaRequestDTO dto) {
        Optional<Especie> especie = especieRepository.findById(dto.especieId());

        if (especie.isEmpty()) {
            return Optional.empty(); // el cliente mandó un especieId que no existe
        }

        Mascota mascota = new Mascota();
        mascota.setNombre(dto.nombre());
        mascota.setRaza(dto.raza());
        mascota.setEdad(dto.edad());
        mascota.setEspecie(especie.get());

        return Optional.of(aResponseDTO(mascotaRepository.save(mascota)));
    }

    @Transactional
    public Optional<MascotaResponseDTO> actualizar(Long id, MascotaRequestDTO dto) {
        Optional<Especie> especie = especieRepository.findById(dto.especieId());

        if (especie.isEmpty()) {
            return Optional.empty();
        }

        return mascotaRepository.findById(id).map(existente -> {
            existente.setNombre(dto.nombre());
            existente.setRaza(dto.raza());
            existente.setEdad(dto.edad());
            existente.setEspecie(especie.get());
            return aResponseDTO(mascotaRepository.save(existente));
        });
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (mascotaRepository.existsById(id)) {
            mascotaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convierte la entidad en su DTO de respuesta
    private MascotaResponseDTO aResponseDTO(Mascota mascota) {
        return new MascotaResponseDTO(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getRaza(),
                mascota.getEdad(),
                new EspecieResumenDTO(mascota.getEspecie().getId(), mascota.getEspecie().getNombre())
        );
    }
}