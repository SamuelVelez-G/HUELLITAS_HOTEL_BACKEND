package com.Huellitas.Hotel.repository;

import com.Huellitas.Hotel.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    // Derived query: Spring Data genera el SQL solo con el nombre del método
    List<Mascota> findByEspecieId(Long especieId);
}