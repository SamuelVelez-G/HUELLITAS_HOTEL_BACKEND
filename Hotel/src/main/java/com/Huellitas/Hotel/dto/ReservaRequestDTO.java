package com.Huellitas.Hotel.dto;

public record ReservaRequestDTO(
        String nombre,
        String raza,
        Integer edad,
        Long especieId,
        Long usuarioId
) {}
