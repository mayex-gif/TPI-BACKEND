package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.DepositoDTO;
import com.backend.ms_logistica.model.Deposito;
import com.backend.ms_logistica.service.DepositoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/depositos")
public class DepositoController {

    @Autowired
    private DepositoService service;

    @GetMapping
    public ResponseEntity<List<DepositoDTO>> listar() {
        return ResponseEntity.ok(service.listarDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepositoDTO> buscar(@PathVariable Integer id) {
        Deposito d = service.buscar(id);
        return ResponseEntity.ok(DepositoService.toDTO(d));
    }

    @PostMapping
    public ResponseEntity<DepositoDTO> crear(@Valid @RequestBody DepositoDTO dto) {
        Deposito d = DepositoService.toEntity(dto);
        Deposito creado = service.crear(d);
        return ResponseEntity.ok(DepositoService.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepositoDTO> actualizar(@PathVariable Integer id,
                                                  @Valid @RequestBody DepositoDTO dto) {
        Deposito d = DepositoService.toEntity(dto);
        Deposito actualizado = service.actualizar(id, d);
        return ResponseEntity.ok(DepositoService.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
