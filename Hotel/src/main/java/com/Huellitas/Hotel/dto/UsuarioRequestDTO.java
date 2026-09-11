package com.Huellitas.Hotel.dto;

import com.Huellitas.Hotel.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        @NotBlank(message = "El teléfono es obligatorio")
        @Size(min = 10, message = "El teléfono debe tener 10 dígitos")
        String telefono,
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe tener un formato válido")
        String email,
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String contrasena
) {
}