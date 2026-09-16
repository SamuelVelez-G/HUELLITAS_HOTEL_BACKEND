package com.Huellitas.Hotel.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaRequestDTO(
        @NotNull(message = "El ID del usuario es obligatorio")
        Long usuarioId,

        @NotNull(message = "La fecha de inicio es obligatoria")
        @Future(message = "La fecha de inicio debe ser una fecha futura")
        LocalDateTime fechaInicio,

        @NotNull(message = "La fecha de fin es obligatoria")
        @Future(message = "La fecha de fin debe ser una fecha futura")
        LocalDateTime fechaFin,

        @NotNull(message = "El valor total es obligatorio")
        @Positive(message = "El total debe ser mayor a cero")
        BigDecimal total
) {}
