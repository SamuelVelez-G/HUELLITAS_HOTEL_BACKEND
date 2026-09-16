package com.Huellitas.Hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MascotaRequestDTO(
        @NotBlank(message = "El nombre de la mascota es obligatorio")
        String nombre,
        String raza,
        @Positive(message = "La edad debe ser un número positivo")
        Integer edad,
        @NotNull(message = "El ID de la especie es obligatorio")
        Long especieId,
        @NotNull(message = "El ID del usuario/dueño es obligatorio")
        Long usuarioId
) {}