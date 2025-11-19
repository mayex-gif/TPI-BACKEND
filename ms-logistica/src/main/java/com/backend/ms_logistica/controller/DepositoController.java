package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.DepositoDTO;
import com.backend.ms_logistica.service.DepositoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/depositos")
public class DepositoController {

    private final DepositoService depositoService;

    public DepositoController(DepositoService depositoService) {
        this.depositoService = depositoService;
    }

    @GetMapping
    public ResponseEntity<List<DepositoDTO>> obtenerTodos() {
        List<DepositoDTO> depositos = depositoService.obtenerTodos();
        return ResponseEntity.ok(depositos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepositoDTO> obtenerPorId(@PathVariable Integer id) {
        DepositoDTO deposito = depositoService.obtenerPorId(id);
        return ResponseEntity.ok(deposito);
    }

    @PostMapping
    public ResponseEntity<DepositoDTO> crear(@Valid @RequestBody DepositoDTO dto) {
        DepositoDTO nuevoDeposito = depositoService.crear(dto);
        return new ResponseEntity<>(nuevoDeposito, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepositoDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DepositoDTO dto) {
        DepositoDTO actualizado = depositoService.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        depositoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cercanos")
    public ResponseEntity<List<DepositoDTO>> buscarCercanos(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam Double radioKm) {
        List<DepositoDTO> cercanos = depositoService.buscarCercanos(lat, lon, radioKm);
        return ResponseEntity.ok(cercanos);
    }
}