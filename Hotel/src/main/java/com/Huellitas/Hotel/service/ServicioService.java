package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.dto.EspecieResponseDTO;
import com.Huellitas.Hotel.dto.ServicioRequestDTO;
import com.Huellitas.Hotel.dto.ServicioResponseDTO;
import com.Huellitas.Hotel.model.Especie;
import com.Huellitas.Hotel.model.Servicio;
import com.Huellitas.Hotel.repository.EspecieRepository;
import com.Huellitas.Hotel.repository.ServicioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;
    private final EspecieRepository especieRepository; // <--- Inyección necesaria para enlazar la especie

    @Transactional(readOnly = true)
    public List<ServicioResponseDTO> listarTodos() {
        return servicioRepository.findAll()
                .stream()
                .map(this::aResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ServicioResponseDTO buscarPorId(Long id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado con id: " + id));
        return aResponseDTO(servicio);
    }

    @Transactional
    public ServicioResponseDTO guardar(ServicioRequestDTO dto) {
        Especie especie = especieRepository.findById(dto.idEspecie())
                .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada con id: " + dto.idEspecie()));

        Servicio servicio = new Servicio();
        servicio.setNombre(dto.nombre());
        servicio.setImagen(dto.imagen());
        servicio.setDescripcion(dto.descripcion());
        servicio.setPrecio(dto.precio());
        servicio.setDisponible(dto.disponible());
        servicio.setFechaCreacion(LocalDateTime.now());
        servicio.setEspecie(especie);

        Servicio guardado = servicioRepository.save(servicio);
        return aResponseDTO(guardado);
    }

    @Transactional
    public ServicioResponseDTO actualizar(Long id, ServicioRequestDTO dto) {
        Servicio servicioExistente = servicioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado con id: " + id));

        Especie especie = especieRepository.findById(dto.idEspecie())
                .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada con id: " + dto.idEspecie()));

        servicioExistente.setNombre(dto.nombre());
        servicioExistente.setImagen(dto.imagen());
        servicioExistente.setDescripcion(dto.descripcion());
        servicioExistente.setPrecio(dto.precio());
        servicioExistente.setDisponible(dto.disponible());
        servicioExistente.setEspecie(especie);

        Servicio actualizado = servicioRepository.save(servicioExistente);
        return aResponseDTO(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new EntityNotFoundException("Servicio no encontrado con id: " + id);
        }
        servicioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ServicioResponseDTO> listarDisponibles() {
        return servicioRepository.findByDisponibleTrue()
                .stream()
                .map(this::aResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ServicioResponseDTO> listarPorEspecie(Long especieId) {
        return servicioRepository.findByEspecieId(especieId)
                .stream()
                .map(this::aResponseDTO)
                .toList();
    }


    private ServicioResponseDTO aResponseDTO(Servicio servicio) {
        EspecieResponseDTO especieDTO = servicio.getEspecie() != null
                ? new EspecieResponseDTO(servicio.getEspecie().getId(), servicio.getEspecie().getNombre())
                : null;

        return new ServicioResponseDTO(
                servicio.getId(),
                servicio.getNombre(),
                servicio.getImagen(),
                servicio.getDescripcion(),
                servicio.getPrecio(),
                servicio.getDisponible(),
                servicio.getFechaCreacion(),
                especieDTO
        );
    }
}