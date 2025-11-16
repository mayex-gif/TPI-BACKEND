package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.CamionDTO;
import com.backend.ms_logistica.model.Camion;
import com.backend.ms_logistica.service.CamionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v0.1/camiones")
public class CamionController {

    @Autowired
    private CamionService service;

    @GetMapping
    public ResponseEntity<List<CamionDTO>> listar() {
        List<CamionDTO> dtos = service.listar().stream()
                .map(CamionService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{patente}")
    public ResponseEntity<CamionDTO> buscar(@PathVariable String patente) {
        return ResponseEntity.ok(CamionService.toDTO(service.buscar(patente)));
    }

    @PostMapping
    public ResponseEntity<CamionDTO> crear(@RequestBody CamionDTO dto) {
        Camion c = service.crear(CamionService.toEntity(dto));
        return ResponseEntity.ok(CamionService.toDTO(c));
    }

    @PutMapping("/{patente}")
    public ResponseEntity<CamionDTO> actualizar(@PathVariable String patente, @RequestBody CamionDTO dto) {
        Camion c = service.actualizar(patente, CamionService.toEntity(dto));
        return ResponseEntity.ok(CamionService.toDTO(c));
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminar(@PathVariable String patente) {
        service.eliminar(patente);
        return ResponseEntity.noContent().build();
    }
}
