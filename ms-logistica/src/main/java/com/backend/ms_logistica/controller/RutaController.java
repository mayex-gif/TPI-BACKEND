package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.RutaDTO;
import com.backend.ms_logistica.model.Ruta;
import com.backend.ms_logistica.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v0.1/rutas")
public class RutaController {

    @Autowired
    private RutaService service;

    @GetMapping
    public ResponseEntity<List<RutaDTO>> listar() {
        List<RutaDTO> rutas = service.listar().stream()
                .map(service::toDTO)
                .toList();
        return ResponseEntity.ok(rutas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutaDTO> buscar(@PathVariable Integer id) {
        Ruta r = service.buscar(id);
        return ResponseEntity.ok(service.toDTO(r));
    }

    @PostMapping
    public ResponseEntity<RutaDTO> crear(@Valid @RequestBody RutaDTO dto) {
        Ruta r = service.toEntity(dto);
        Ruta creado = service.crear(r);
        return ResponseEntity.ok(service.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RutaDTO> actualizar(@PathVariable Integer id,
                                              @Valid @RequestBody RutaDTO dto) {
        Ruta r = service.toEntity(dto);
        Ruta actualizado = service.actualizar(id, r);
        return ResponseEntity.ok(service.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
