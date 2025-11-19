package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.TramoDTO;
import com.backend.ms_logistica.service.TramoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/tramos")
public class TramoController {

    private final TramoService tramoService;

    public TramoController(TramoService tramoService) {
        this.tramoService = tramoService;
    }

    // ===================================
    // CRUD BÁSICO
    // ===================================

    @GetMapping
    public ResponseEntity<List<TramoDTO>> obtenerTodos() {
        List<TramoDTO> tramos = tramoService.obtenerTodos();
        return ResponseEntity.ok(tramos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TramoDTO> obtenerPorId(@PathVariable Integer id) {
        TramoDTO tramo = tramoService.obtenerPorId(id);
        return ResponseEntity.ok(tramo);
    }

    @GetMapping("/ruta/{idRuta}")
    public ResponseEntity<List<TramoDTO>> obtenerPorRuta(@PathVariable Integer idRuta) {
        List<TramoDTO> tramos = tramoService.obtenerPorRuta(idRuta);
        return ResponseEntity.ok(tramos);
    }

    // ===================================
    // RF6: ASIGNAR CAMIÓN A TRAMO
    // ===================================

    @PatchMapping("/{id}/asignar-camion")
    public ResponseEntity<TramoDTO> asignarCamion(
            @PathVariable Integer id,
            @RequestParam String patenteCamion) {
        TramoDTO actualizado = tramoService.asignarCamion(id, patenteCamion);
        return ResponseEntity.ok(actualizado);
    }

    // ===================================
    // RF7: INICIAR/FINALIZAR TRAMO (Transportista)
    // ===================================

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<TramoDTO> iniciarTramo(@PathVariable Integer id) {
        TramoDTO actualizado = tramoService.iniciarTramo(id);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<TramoDTO> finalizarTramo(@PathVariable Integer id) {
        TramoDTO actualizado = tramoService.finalizarTramo(id);
        return ResponseEntity.ok(actualizado);
    }

    // ===================================
    // CONSULTAS DE GESTIÓN
    // ===================================

    @GetMapping("/en-progreso")
    public ResponseEntity<List<TramoDTO>> obtenerTramosEnProgreso() {
        List<TramoDTO> tramos = tramoService.obtenerTramosEnProgreso();
        return ResponseEntity.ok(tramos);
    }

    @GetMapping("/pendientes-asignacion")
    public ResponseEntity<List<TramoDTO>> obtenerTramosPendientesAsignacion() {
        List<TramoDTO> tramos = tramoService.obtenerTramosPendientesAsignacion();
        return ResponseEntity.ok(tramos);
    }
}