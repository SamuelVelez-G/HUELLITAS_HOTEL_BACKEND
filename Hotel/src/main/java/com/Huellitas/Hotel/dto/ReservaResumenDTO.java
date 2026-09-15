package com.Huellitas.Hotel.dto;

import java.time.LocalDateTime;

public record ReservaResumenDTO(
        Long id,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
) {
}