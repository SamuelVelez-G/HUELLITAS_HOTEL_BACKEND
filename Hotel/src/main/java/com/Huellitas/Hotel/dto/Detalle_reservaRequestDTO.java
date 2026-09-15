package com.Huellitas.Hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detalle_reservaRequestDTO {
    private Long reservaId;
    private Long mascotaId;
    private Long servicioId;
    private int cantidadDeMascotas;
    // opcional: si se envía, se usará; si es null, se tomará el precio del servicio
    private Double precioUnitario;
}
