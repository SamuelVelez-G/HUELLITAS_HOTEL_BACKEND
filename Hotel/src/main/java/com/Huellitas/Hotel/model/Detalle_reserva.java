package com.Huellitas.Hotel.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "detalles_reserva")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Detalle_reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tipo_de_servicio", nullable = false)
    private String tipoDeServicio;
    @Column(name = "cantidad_de_mascotas", nullable = false)
    private Int cantidadDeMascotas;
    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;
    private Double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;
}
