package com.Huellitas.Hotel.model;
import jakarta.persistence.Entity;
import jakarta.persistence.id;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "especies")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Especie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @OneToMany(mappedBy = "especie")
    private List<Mascota> mascotas;
@oneToMany(mappedBy = "especie")
private List<Servicio> servicios;
}
