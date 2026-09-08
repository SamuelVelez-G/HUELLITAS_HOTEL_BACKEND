package com.Huellitas.Hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = 'reservas')
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva {

    @Id
    @GenerateValue(strategy = GenerationType.IDENTY)
    private Long id;
    private int id_usuario ;
    @Colum(name = "fecha_reserva")
    private Date fecha_reserva;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String estado;
    private Double total ;
    private int id_detalle ;

}
