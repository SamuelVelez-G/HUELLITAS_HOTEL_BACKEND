package com.Huellitas.Hotel.controller;

import com.Huellitas.Hotel.dto.ServicioRequestDTO;
import com.Huellitas.Hotel.dto.ServicioResponseDTO;
import com.Huellitas.Hotel.model.Servicio;
import com.Huellitas.Hotel.service.ServicioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<ServicioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(servicioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioService.buscarPorId(id));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ServicioResponseDTO>> listarDisponibles() {
        return ResponseEntity.ok(servicioService.listarDisponibles());
    }

    @GetMapping("/especie/{especieId}")
    public ResponseEntity<List<ServicioResponseDTO>> listarPorEspecie(@PathVariable Long especieId) {
        return ResponseEntity.ok(servicioService.listarPorEspecie(especieId));
    }

    @PostMapping
    public ResponseEntity<ServicioResponseDTO> crear(@RequestBody @Valid ServicioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> actualizar(@PathVariable Long id, @RequestBody @Valid ServicioRequestDTO dto) {
        return ResponseEntity.ok(servicioService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}