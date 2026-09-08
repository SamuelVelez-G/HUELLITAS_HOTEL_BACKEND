package com.Huellitas.Hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String raza;

    private Integer edad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especie_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Especie especie;

    @OneToMany(mappedBy = "mascota")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Detalle_reserva> detalles = new ArrayList<>();
}