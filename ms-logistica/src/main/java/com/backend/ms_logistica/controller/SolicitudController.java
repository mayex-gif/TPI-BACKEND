package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.EstadoContenedorDTO;
import com.backend.ms_logistica.dto.RutaDTO;
import com.backend.ms_logistica.dto.SolicitudCreateDTO;
import com.backend.ms_logistica.dto.SolicitudDTO;
import com.backend.ms_logistica.service.SolicitudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    // ===================================
    // CRUD: OBTENER TODAS
    // ===================================
    @GetMapping
    public ResponseEntity<List<SolicitudDTO>> obtenerTodas() {
        List<SolicitudDTO> solicitudes = solicitudService.obtenerTodas();
        return ResponseEntity.ok(solicitudes);
    }

    // ===================================
    // CRUD: OBTENER POR ID
    // ===================================
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDTO> obtenerPorId(@PathVariable Integer id) {
        SolicitudDTO solicitud = solicitudService.obtenerPorId(id);
        return ResponseEntity.ok(solicitud);
    }

    // ===================================
    // CRUD: OBTENER POR CLIENTE
    // ===================================
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<SolicitudDTO>> obtenerPorCliente(@PathVariable Integer idCliente) {
        List<SolicitudDTO> solicitudes = solicitudService.obtenerPorCliente(idCliente);
        return ResponseEntity.ok(solicitudes);
    }

    // ===================================
    // RF1: CREAR NUEVA SOLICITUD
    // ===================================
    @PostMapping
    public ResponseEntity<SolicitudDTO> crearSolicitud(
            @Valid @RequestBody SolicitudCreateDTO dto) {
        SolicitudDTO nuevaSolicitud = solicitudService.crearSolicitud(dto);
        return new ResponseEntity<>(nuevaSolicitud, HttpStatus.CREATED);
    }

    // ===================================
    // RF3: CONSULTAR RUTAS TENTATIVAS
    // ===================================
    @GetMapping("/{id}/rutas-tentativas")
    public ResponseEntity<List<RutaDTO>> consultarRutasTentativas(@PathVariable Integer id) {
        List<RutaDTO> rutas = solicitudService.consultarRutasTentativas(id);
        return ResponseEntity.ok(rutas);
    }

    // ===================================
    // RF4: ASIGNAR RUTA A SOLICITUD
    // ===================================
    @PatchMapping("/{id}/asignar-ruta")
    public ResponseEntity<SolicitudDTO> asignarRuta(
            @PathVariable Integer id,
            @RequestBody(required = false) List<Integer> idsDepositos) {
        SolicitudDTO actualizada = solicitudService.asignarRuta(id, idsDepositos);
        return ResponseEntity.ok(actualizada);
    }

    // ===================================
    // RF2: CONSULTAR ESTADO DEL TRANSPORTE
    // ===================================
    @GetMapping("/{id}/estado-transporte")
    public ResponseEntity<EstadoContenedorDTO> consultarEstadoContenedor(
            @PathVariable Integer id) {
        EstadoContenedorDTO estado = solicitudService.consultarEstadoContenedor(id);
        return ResponseEntity.ok(estado);
    }

    // ===================================
    // RF5: CONSULTAR SOLICITUDES PENDIENTES
    // ===================================
    @GetMapping("/pendientes")
    public ResponseEntity<List<SolicitudDTO>> obtenerSolicitudesPendientes(
            @RequestParam(required = false) String estadoFiltro) {
        List<SolicitudDTO> pendientes = solicitudService
                .obtenerSolicitudesPendientes(estadoFiltro);
        return ResponseEntity.ok(pendientes);
    }

    // ===================================
    // RF10: FINALIZAR SOLICITUD
    // ===================================
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<SolicitudDTO> finalizarSolicitud(@PathVariable Integer id) {
        SolicitudDTO finalizada = solicitudService.finalizarSolicitud(id);
        return ResponseEntity.ok(finalizada);
    }
}
