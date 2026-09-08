package com.Huellitas.Hotel.dto;

import com.Huellitas.Hotel.model.Mascota;

import java.util.List;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String telefono,
        String email,
        List<MascotaResumenDTO> mascotas

){

}
