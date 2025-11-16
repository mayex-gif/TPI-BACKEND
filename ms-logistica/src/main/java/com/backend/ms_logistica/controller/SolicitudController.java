package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.SolicitudDTO;
import com.backend.ms_logistica.model.Solicitud;
import com.backend.ms_logistica.service.SolicitudService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    // Crear solicitud
    @PostMapping
    public ResponseEntity<Solicitud> crear(@Valid @RequestBody SolicitudDTO dto) {
        Solicitud s = solicitudService.crear(dto);
        return ResponseEntity.ok(s);
    }

    // Listar todas las solicitudes
    @GetMapping
    public ResponseEntity<List<Solicitud>> listar() {
        List<Solicitud> lista = solicitudService.listar();
        return ResponseEntity.ok(lista);
    }

    // Buscar solicitud por ID
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> buscar(@PathVariable Integer id) {
        Solicitud s = solicitudService.buscar(id);
        return ResponseEntity.ok(s);
    }

    // Actualizar solicitud
    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizar(@PathVariable Integer id,
                                                @Valid @RequestBody SolicitudDTO dto) {  // 👈 @Valid
        Solicitud s = solicitudService.actualizar(id, dto);
        return ResponseEntity.ok(s);
    }

    // Eliminar solicitud
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        solicitudService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
