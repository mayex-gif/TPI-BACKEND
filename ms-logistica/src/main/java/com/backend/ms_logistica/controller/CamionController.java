package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.CamionDTO;
import com.backend.ms_logistica.service.CamionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/camiones")
public class CamionController {

    private final CamionService camionService;

    public CamionController(CamionService camionService) {
        this.camionService = camionService;
    }

    @GetMapping
    public ResponseEntity<List<CamionDTO>> obtenerTodos() {
        List<CamionDTO> camiones = camionService.obtenerTodos();
        return ResponseEntity.ok(camiones);
    }

    @GetMapping("/{patente}")
    public ResponseEntity<CamionDTO> obtenerPorPatente(@PathVariable String patente) {
        CamionDTO camion = camionService.obtenerPorPatente(patente);
        return ResponseEntity.ok(camion);
    }

    @PostMapping
    public ResponseEntity<CamionDTO> crear(@Valid @RequestBody CamionDTO dto) {
        CamionDTO nuevoCamion = camionService.crear(dto);
        return new ResponseEntity<>(nuevoCamion, HttpStatus.CREATED);
    }

    @PutMapping("/{patente}")
    public ResponseEntity<CamionDTO> actualizar(
            @PathVariable String patente,
            @Valid @RequestBody CamionDTO dto) {
        CamionDTO actualizado = camionService.actualizar(patente, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminar(@PathVariable String patente) {
        camionService.eliminar(patente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<CamionDTO>> obtenerDisponibles() {
        List<CamionDTO> camiones = camionService.obtenerDisponibles();
        return ResponseEntity.ok(camiones);
    }

    @GetMapping("/buscar-compatibles")
    public ResponseEntity<List<CamionDTO>> buscarCompatibles(
            @RequestParam Double pesoRequerido,
            @RequestParam Double volumenRequerido) {
        List<CamionDTO> compatibles = camionService
                .buscarCompatibles(pesoRequerido, volumenRequerido);
        return ResponseEntity.ok(compatibles);
    }

    @PatchMapping("/{patente}/ocupar")
    public ResponseEntity<Void> marcarComoOcupado(@PathVariable String patente) {
        camionService.marcarComoOcupado(patente);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{patente}/liberar")
    public ResponseEntity<Void> marcarComoDisponible(@PathVariable String patente) {
        camionService.marcarComoDisponible(patente);
        return ResponseEntity.ok().build();
    }
}