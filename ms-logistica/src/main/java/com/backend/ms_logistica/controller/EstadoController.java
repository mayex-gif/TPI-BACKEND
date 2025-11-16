package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.EstadoDTO;
import com.backend.ms_logistica.model.Estado;
import com.backend.ms_logistica.service.EstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/estados")
public class EstadoController {

    @Autowired
    private EstadoService service;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> listar() {
        return ResponseEntity.ok(service.listarDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> buscar(@PathVariable Integer id) {
        Estado e = service.buscar(id);
        return ResponseEntity.ok(EstadoService.toDTO(e));
    }

    @PostMapping
    public ResponseEntity<EstadoDTO> crear(@Valid @RequestBody EstadoDTO dto) {
        Estado e = EstadoService.toEntity(dto);
        Estado creado = service.crear(e);
        return ResponseEntity.ok(EstadoService.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDTO> actualizar(@PathVariable Integer id,
                                                @Valid @RequestBody EstadoDTO dto) {
        Estado e = EstadoService.toEntity(dto);
        Estado actualizado = service.actualizar(id, e);
        return ResponseEntity.ok(EstadoService.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
