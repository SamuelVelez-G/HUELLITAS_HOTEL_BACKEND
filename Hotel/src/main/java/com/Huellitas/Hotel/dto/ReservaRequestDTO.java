package com.Huellitas.Hotel.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaRequestDTO(
        @NotNull(message = "El ID del usuario es obligatorio")
        Long usuarioId,
        @NotNull(message = "La fecha de inicio es obligatoria")
        LocalDateTime fechaInicio,
        @NotNull(message = "La fecha de fin es obligatoria")
        LocalDateTime fechaFin,
        @NotNull(message = "El total es obligatorio")
        BigDecimal total
) {
}