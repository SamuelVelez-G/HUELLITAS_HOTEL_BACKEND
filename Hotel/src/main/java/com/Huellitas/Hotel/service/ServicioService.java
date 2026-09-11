package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.model.Servicio;
import com.Huellitas.Hotel.repository.ServicioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> buscarPorId(Long id) {
        return servicioRepository.findById(id);
    }

    public Servicio guardar(Servicio servicio) {
        servicio.setFechaCreacion(LocalDateTime.now());
        return servicioRepository.save(servicio);
    }

    public Servicio actualizar(Long id, Servicio servicioActualizado) {
        Servicio servicioExistente = servicioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado con id: " + id));

        servicioExistente.setNombre(servicioActualizado.getNombre());
        servicioExistente.setImagen(servicioActualizado.getImagen());
        servicioExistente.setDescripcion(servicioActualizado.getDescripcion());
        servicioExistente.setPrecio(servicioActualizado.getPrecio());
        servicioExistente.setDisponible(servicioActualizado.getDisponible());
        servicioExistente.setEspecie(servicioActualizado.getEspecie());

        return servicioRepository.save(servicioExistente);
    }

    public void eliminar(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new EntityNotFoundException("Servicio no encontrado con id: " + id);
        }
        servicioRepository.deleteById(id);
    }

    public List<Servicio> listarDisponibles() {
        return servicioRepository.findByDisponibleTrue();
    }

    public List<Servicio> listarPorEspecie(Long especieId) {
        return servicioRepository.findByEspecieId(especieId);
    }
}