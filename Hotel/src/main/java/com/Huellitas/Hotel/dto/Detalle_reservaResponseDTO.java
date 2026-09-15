package com.Huellitas.Hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detalle_reservaResponseDTO {
    private Long id;
    private String tipoDeServicio;
    private int cantidadDeMascotas;
    private double precioUnitario;
    private double subtotal;
    private Long mascotaId;
    private Long servicioId;
    private Long reservaId;
}
