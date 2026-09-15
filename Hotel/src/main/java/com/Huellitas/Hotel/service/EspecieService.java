package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.dto.EspecieRequestDTO;
import com.Huellitas.Hotel.dto.EspecieResponseDTO;
import com.Huellitas.Hotel.model.Especie;
import com.Huellitas.Hotel.repository.EspecieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EspecieService {

    private final EspecieRepository especieRepository;

    public EspecieService(EspecieRepository especieRepository) {
        this.especieRepository = especieRepository;
    }

    @Transactional(readOnly = true)
    public List<EspecieResponseDTO> listarTodas() {
        return especieRepository.findAll()
                .stream()
                .map(especie -> new EspecieResponseDTO(especie.getId(), especie.getNombre()))
                .toList();
    }

    @Transactional(readOnly = true)
    public EspecieResponseDTO obtenerPorId(Long id) {
        Especie especie = especieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada con id: " + id));
        return new EspecieResponseDTO(especie.getId(), especie.getNombre());
    }

    @Transactional
    public EspecieResponseDTO guardar(EspecieRequestDTO dto) {
        Especie especie = new Especie();
        especie.setNombre(dto.nombre());

        Especie guardada = especieRepository.save(especie);
        return new EspecieResponseDTO(guardada.getId(), guardada.getNombre());
    }

    @Transactional
    public EspecieResponseDTO actualizar(Long id, EspecieRequestDTO dto) {
        // Buscamos la entidad real en BD para modificarla
        Especie especieExistente = especieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada con id: " + id));

        especieExistente.setNombre(dto.nombre());
        Especie actualizada = especieRepository.save(especieExistente);

        return new EspecieResponseDTO(actualizada.getId(), actualizada.getNombre());
    }

    @Transactional
    public void eliminar(Long id) {
        if (!especieRepository.existsById(id)) {
            throw new EntityNotFoundException("Especie no encontrada con id: " + id);
        }
        especieRepository.deleteById(id);
    }
}