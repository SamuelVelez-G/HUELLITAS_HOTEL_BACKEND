package com.Huellitas.Hotel.dto;

import com.Huellitas.Hotel.model.Rol;

public record LoginResponseDTO(
        String token,
        String username,
        Rol rol
) {
}
