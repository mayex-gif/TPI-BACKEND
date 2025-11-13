package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.RutaDTO;
import com.backend.ms_logistica.model.Ruta;
import com.backend.ms_logistica.remoto.SolicitudDTO;
import com.backend.ms_logistica.service.RutaService;
import org.springframework.http.HttpStatus;
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

//    @GetMapping
//    public List<Ruta> listarTodos() {
//        return rutaService.listarTodos();
//    }

    @GetMapping
    public ResponseEntity<List<Ruta>> listar(
            @RequestParam(value = "solicitudId", required = false) Integer solicitudId) {

        if (solicitudId != null) {
            List<Ruta> rutas = rutaService.obtenerPorIdSolicitud(solicitudId);
            return ResponseEntity.ok(rutas);
        }

        return ResponseEntity.ok(rutaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutaDTO> obtenerPorId(@PathVariable Integer id) {
        Ruta ruta = rutaService.obtenerPorId(id);
        if (ruta == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDTO(ruta));
    }

    @PostMapping
    public ResponseEntity<Ruta> crear(@RequestBody RutaDTO dto) {
        Ruta guardado = rutaService.crearRutaConSolicitud(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
//    private Integer id_ruta;
//    private int cantidad_tramos;
//    private int cantidad_depositos;
//    private Double distance_total;
//    private Integer id_solicitud;
    // --- Conversión manual sin mapper ---
    private RutaDTO toDTO(Ruta r) {
        RutaDTO dto = new RutaDTO();
        dto.setId_ruta(r.getId_ruta());
        dto.setCantidad_tramos(r.getCantidad_tramos());
        dto.setCantidad_depositos(r.getCantidad_depositos());
        dto.setDistance_total(r.getDistance_total());
        dto.setId_solicitud(r.getIdSolicitud());
        return dto;
    }
}
