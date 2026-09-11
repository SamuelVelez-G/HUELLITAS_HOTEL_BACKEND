package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.model.Especie;
import com.Huellitas.Hotel.repository.EspecieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecieService {

    @Autowired
    private EspecieRepository especieRepository;

    public List<Especie> listarTodas() {
        return especieRepository.findAll();
    }

    public Especie obtenerPorId(Long id) {
        return especieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada con id: " + id));
    }

    public Especie guardar(Especie especie) {
        return especieRepository.save(especie);
    }

    public Especie actualizar(Long id, Especie especie) {
        Especie existente = obtenerPorId(id);
        existente.setNombre(especie.getNombre());
        return especieRepository.save(existente);
    }

    public void eliminar(Long id) {
        Especie existente = obtenerPorId(id);
        especieRepository.delete(existente);
    }
}