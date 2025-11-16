package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.TramoDTO;
import com.backend.ms_logistica.model.Tramo;
import com.backend.ms_logistica.service.TramoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v0.1/tramos")
public class TramoController {

    @Autowired
    private TramoService service;

    @GetMapping
    public ResponseEntity<List<TramoDTO>> listar() {
        List<TramoDTO> dtos = service.listar().stream()
                .map(service::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TramoDTO> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.toDTO(service.buscar(id)));
    }

    @PostMapping
    public ResponseEntity<TramoDTO> crear(@Valid @RequestBody TramoDTO dto) {
        Tramo t = service.toEntity(dto);
        return ResponseEntity.ok(service.toDTO(service.crear(t)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TramoDTO> actualizar(@PathVariable Integer id,
                                               @Valid @RequestBody TramoDTO dto) {
        Tramo t = service.toEntity(dto);
        return ResponseEntity.ok(service.toDTO(service.actualizar(id, t)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
