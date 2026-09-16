package com.Huellitas.Hotel.controller;

import com.Huellitas.Hotel.dto.ReservaRequestDTO;
import com.Huellitas.Hotel.dto.ReservaResponseDTO;
import com.Huellitas.Hotel.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(@RequestBody @Valid ReservaRequestDTO datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crearReserva(datos));
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> buscarReservas() {
        return ResponseEntity.ok(reservaService.buscarReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarReservaId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarReservaId(id));
    }
}