package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.RutaDTO;
import com.backend.ms_logistica.service.RutaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/rutas")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @GetMapping
    public ResponseEntity<List<RutaDTO>> obtenerTodas() {
        List<RutaDTO> rutas = rutaService.obtenerTodas();
        return ResponseEntity.ok(rutas);
    }

    // ===================================
    // OBTENER POR ID
    // ===================================
    @GetMapping("/{id}")
    public ResponseEntity<RutaDTO> obtenerPorId(@PathVariable Integer id) {
        RutaDTO ruta = rutaService.obtenerPorId(id);
        return ResponseEntity.ok(ruta);
    }

    // ===================================
    // OBTENER POR ID SOLICITUD
    // ===================================
    @GetMapping("/solicitud/{idSolicitud}")
    public ResponseEntity<RutaDTO> obtenerPorSolicitud(@PathVariable Integer idSolicitud) {
        RutaDTO ruta = rutaService.obtenerPorSolicitud(idSolicitud);
        return ResponseEntity.ok(ruta);
    }

}