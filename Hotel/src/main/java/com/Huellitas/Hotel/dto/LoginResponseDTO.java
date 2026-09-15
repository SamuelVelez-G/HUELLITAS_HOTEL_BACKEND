package com.Huellitas.Hotel.dto;

import com.Huellitas.Hotel.model.RolUsuario;

public record LoginResponseDTO(
        String token,
        String email,
        RolUsuario rol
) {
}