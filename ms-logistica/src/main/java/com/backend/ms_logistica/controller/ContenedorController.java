package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.ContenedorDTO;
import com.backend.ms_logistica.model.Contenedor;
import com.backend.ms_logistica.service.ContenedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/contenedores")
public class ContenedorController {

    @Autowired
    private ContenedorService service;

    @GetMapping
    public ResponseEntity<List<ContenedorDTO>> listar() {
        return ResponseEntity.ok(service.listarDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContenedorDTO> buscar(@PathVariable Integer id) {
        Contenedor c = service.buscar(id);
        return ResponseEntity.ok(service.toDTO(c));
    }

    @PostMapping
    public ResponseEntity<ContenedorDTO> crear(@Valid @RequestBody ContenedorDTO dto) {
        Contenedor c = service.toEntity(dto);
        Contenedor creado = service.crear(c);
        return ResponseEntity.ok(service.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContenedorDTO> actualizar(@PathVariable Integer id,
                                                    @Valid @RequestBody ContenedorDTO dto) {
        Contenedor c = service.toEntity(dto);
        Contenedor actualizado = service.actualizar(id, c);
        return ResponseEntity.ok(service.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
