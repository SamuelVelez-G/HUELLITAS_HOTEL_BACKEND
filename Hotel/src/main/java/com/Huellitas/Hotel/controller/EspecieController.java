package com.Huellitas.Hotel.controller;

import com.Huellitas.Hotel.dto.EspecieRequestDTO;
import com.Huellitas.Hotel.dto.EspecieResponseDTO;
import com.Huellitas.Hotel.service.EspecieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especies")
public class EspecieController {

    private final EspecieService especieService;

    public EspecieController(EspecieService especieService) {
        this.especieService = especieService;
    }

    @GetMapping
    public ResponseEntity<List<EspecieResponseDTO>> listarTodas() {
        return ResponseEntity.ok(especieService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecieResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(especieService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<EspecieResponseDTO> crear(@RequestBody @Valid EspecieRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(especieService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecieResponseDTO> actualizar(@PathVariable Long id, @RequestBody @Valid EspecieRequestDTO especie) {
        return ResponseEntity.ok(especieService.actualizar(id, especie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        especieService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}