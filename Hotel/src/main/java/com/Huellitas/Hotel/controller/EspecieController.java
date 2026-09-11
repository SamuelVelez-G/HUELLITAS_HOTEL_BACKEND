package com.Huellitas.Hotel.controller;

import com.Huellitas.Hotel.model.Especie;
import com.Huellitas.Hotel.service.EspecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especies")
public class EspecieController {

    @Autowired
    private EspecieService especieService;

    @GetMapping
    public ResponseEntity<List<Especie>> listarTodas() {
        return ResponseEntity.ok(especieService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especie> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(especieService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Especie> crear(@RequestBody Especie especie) {
        Especie nueva = especieService.guardar(especie);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Especie> actualizar(@PathVariable Long id, @RequestBody Especie especie) {
        return ResponseEntity.ok(especieService.actualizar(id, especie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        especieService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}