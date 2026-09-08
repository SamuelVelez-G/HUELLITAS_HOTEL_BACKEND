package com.Huellitas.Hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String contrasena;
    @Column(nullable = false)
    private String rol;
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
    @OneToMany(mappedBy = "usuario")
    private List<Mascota> mascotas = new ArrayList<>();
    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas = new ArrayList<>();



}
