package com.Huellitas.Hotel.controller;

import com.Huellitas.Hotel.model.Servicio;
import com.Huellitas.Hotel.service.ServicioService;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<List<Servicio>> listarTodos() {
        return ResponseEntity.ok(servicioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> buscarPorId(@PathVariable Long id) {
        return servicioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Servicio>> listarDisponibles() {
        return ResponseEntity.ok(servicioService.listarDisponibles());
    }

    @GetMapping("/especie/{especieId}")
    public ResponseEntity<List<Servicio>> listarPorEspecie(@PathVariable Long especieId) {
        return ResponseEntity.ok(servicioService.listarPorEspecie(especieId));
    }

    @PostMapping
    public ResponseEntity<Servicio> crear(@RequestBody Servicio servicio) {
        Servicio nuevo = servicioService.guardar(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizar(@PathVariable Long id, @RequestBody Servicio servicio) {
        try {
            return ResponseEntity.ok(servicioService.actualizar(id, servicio));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            servicioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}