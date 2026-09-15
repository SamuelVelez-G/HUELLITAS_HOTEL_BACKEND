package com.Huellitas.Hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServicioRequestDTO(
        @NotBlank(message = "El nombre del servicio es obligatorio")
        String nombre,
        String imagen,
        @NotBlank(message = "La descripción es obligatoria")
        String descripcion,
        @NotNull(message = "El precio es obligatorio")
        Double precio,
        @NotNull(message = "Debe especificar si el servicio está disponible")
        Boolean disponible,
        @NotNull(message = "El ID de la especie es obligatorio")
        Long idEspecie
) {
}
