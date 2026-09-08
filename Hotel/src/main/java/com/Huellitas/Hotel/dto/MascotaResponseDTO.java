package com.Huellitas.Hotel.dto;

public record MascotaResponseDTO(
        Long id,
        String nombre,
        String raza,
        Integer edad,
        EspecieResumenDTO especie
) {}