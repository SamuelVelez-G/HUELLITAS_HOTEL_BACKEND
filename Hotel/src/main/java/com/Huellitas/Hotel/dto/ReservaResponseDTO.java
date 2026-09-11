package com.Huellitas.Hotel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaResponseDTO(
        Long id,
        UsuarioResumenDTO usuario, // Reutilizamos el DTO de tu compañera
        LocalDateTime fechaReserva,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        BigDecimal total
) {
}