package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.EstadoDTO;
import com.backend.ms_logistica.model.AmbitoEstado;
import com.backend.ms_logistica.service.EstadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/estados")
public class EstadoController {

    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> obtenerTodos() {
        List<EstadoDTO> estados = estadoService.obtenerTodos();
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> obtenerPorId(@PathVariable Integer id) {
        EstadoDTO estado = estadoService.obtenerPorId(id);
        return ResponseEntity.ok(estado);
    }

    @GetMapping("/ambito")
    public ResponseEntity<List<EstadoDTO>> obtenerPorAmbito(
            @RequestParam AmbitoEstado ambito) {
        List<EstadoDTO> estados = estadoService.obtenerPorAmbito(ambito);
        return ResponseEntity.ok(estados);
    }

    @PostMapping
    public ResponseEntity<EstadoDTO> crear(@Valid @RequestBody EstadoDTO dto) {
        EstadoDTO nuevoEstado = estadoService.crear(dto);
        return new ResponseEntity<>(nuevoEstado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EstadoDTO dto) {
        EstadoDTO actualizado = estadoService.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        estadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}