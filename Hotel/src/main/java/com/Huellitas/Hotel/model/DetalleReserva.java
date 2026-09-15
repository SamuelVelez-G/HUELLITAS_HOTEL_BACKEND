package com.Huellitas.Hotel.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalles_reservas")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DetalleReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tipo_de_servicio", nullable = false)
    private String tipoDeServicio;
    @Column(name = "cantidad_de_mascotas", nullable = false)
    private int cantidadDeMascotas;
    @Column(name = "precio_unitario", nullable = false)
    private double precioUnitario;
    private double subtotal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;
    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;
}
