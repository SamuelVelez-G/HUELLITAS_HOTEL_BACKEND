package com.Huellitas.Hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detalle_reservaResumenDTO {
    private Long id;
    private String tipoDeServicio;
    private int cantidadDeMascotas;
    private double subtotal;
    private Long servicioId;
    private String servicioNombre;
}
