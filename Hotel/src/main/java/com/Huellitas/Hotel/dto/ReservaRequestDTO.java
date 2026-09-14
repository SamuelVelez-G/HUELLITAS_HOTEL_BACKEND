package com.Huellitas.Hotel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaRequestDTO(
        String nombre,
        String raza,
        Integer edad,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        BigDecimal total,
        Long especieId,
        Long usuarioId
) {}
