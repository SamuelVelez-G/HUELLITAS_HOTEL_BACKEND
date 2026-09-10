package com.Huellitas.Hotel.controller;

import com.Huellitas.Hotel.dto.Detalle_reservaRequestDTO;
import com.Huellitas.Hotel.dto.Detalle_reservaResponseDTO;
import com.Huellitas.Hotel.dto.Detalle_reservaResumenDTO;
import com.Huellitas.Hotel.service.Detalle_reservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
@RequiredArgsConstructor
public class Detalle_reservaController {

n    private final Detalle_reservaService detalleService;

n    @PostMapping
    public ResponseEntity<Detalle_reservaResponseDTO> crear(@RequestBody Detalle_reservaRequestDTO dto) {
        Detalle_reservaResponseDTO saved = detalleService.crear(dto);
        return ResponseEntity.ok(saved);
    }

n    @GetMapping("/{id}")
    public ResponseEntity<Detalle_reservaResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(detalleService.obtenerPorId(id));
    }

n    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<Detalle_reservaResumenDTO>> listarPorReserva(@PathVariable Long reservaId) {
        return ResponseEntity.ok(detalleService.listarPorReserva(reservaId));
    }

n    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        detalleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
