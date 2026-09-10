package com.Huellitas.Hotel.controller;

import com.Huellitas.Hotel.dto.ReservaRequestDTO;
import com.Huellitas.Hotel.dto.ReservaResponseDTO;
import com.Huellitas.Hotel.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(@RequestBody @Valid ReservaRequestDTO datos) {
        ReservaResponseDTO creada = reservaService.crearReserva(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    public List<ReservaResponseDTO> buscarReservas() {
        return reservaService.buscarReservas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarReservaId(@PathVariable Long id) {
        return reservaService.buscarReservaId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}