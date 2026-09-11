package com.Huellitas.Hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnoreProperties("especie")
    private List<Mascota> mascotas;

    @OneToMany(mappedBy = "especie")
    @JsonIgnore
    private List<Servicio> servicios = new ArrayList<>();
}