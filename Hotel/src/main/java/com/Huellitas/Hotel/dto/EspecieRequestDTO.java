package com.Huellitas.Hotel.dto;

import jakarta.validation.constraints.NotBlank;

public record EspecieRequestDTO(
        @NotBlank(message = "El nombre de la especie es obligatorio")
        String nombre
) {
}
