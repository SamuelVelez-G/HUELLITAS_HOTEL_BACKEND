package com.Huellitas.Hotel.dto;

import java.time.LocalDateTime;

public record ServicioResponseDTO(
        Long id,
        String nombre,
        String imagen,
        String descripcion,
        Double precio,
        Boolean disponible,
        LocalDateTime fechaCreacion,
        EspecieResponseDTO especie
) {
}
