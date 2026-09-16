package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.ContenedorDTO;
import com.backend.ms_logistica.service.ContenedorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/contenedores")
public class ContenedorController {

    private final ContenedorService contenedorService;

    public ContenedorController(ContenedorService contenedorService) {
        this.contenedorService = contenedorService;
    }

    @GetMapping
    public ResponseEntity<List<ContenedorDTO>> obtenerTodos() {
        List<ContenedorDTO> contenedores = contenedorService.obtenerTodos();
        return ResponseEntity.ok(contenedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContenedorDTO> obtenerPorId(@PathVariable Integer id) {
        ContenedorDTO contenedor = contenedorService.obtenerPorId(id);
        return ResponseEntity.ok(contenedor);
    }

    @GetMapping("/solicitud/{idSolicitud}")
    public ResponseEntity<ContenedorDTO> obtenerPorSolicitud(
            @PathVariable Integer idSolicitud) {
        ContenedorDTO contenedor = contenedorService.obtenerPorSolicitud(idSolicitud);
        return ResponseEntity.ok(contenedor);
    }

    @GetMapping("/estado")
    public ResponseEntity<List<ContenedorDTO>> obtenerPorEstado(
            @RequestParam String nombreEstado) {
        List<ContenedorDTO> contenedores = contenedorService
                .obtenerPorEstado(nombreEstado);
        return ResponseEntity.ok(contenedores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContenedorDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ContenedorDTO dto) {
        ContenedorDTO actualizado = contenedorService.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ContenedorDTO> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam String nombreEstado) {
        ContenedorDTO actualizado = contenedorService.cambiarEstado(id, nombreEstado);
        return ResponseEntity.ok(actualizado);
    }
}