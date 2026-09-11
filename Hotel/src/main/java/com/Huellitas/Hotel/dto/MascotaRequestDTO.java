package com.Huellitas.Hotel.dto;

public record MascotaRequestDTO(
        String nombre,
        String raza,
        Integer edad,
        Long especieId
        // Long usuarioId  -> descomenta cuando exista Usuario
) {}