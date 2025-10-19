package com.backend.ms_clientes.controller;

import com.backend.ms_clientes.dto.SolicitudDTO;
import com.backend.ms_clientes.model.Solicitud;
import com.backend.ms_clientes.service.SolicitudService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping
    public List<Solicitud> listarTodas() {
        return solicitudService.listarTodos();
    }

    @GetMapping("/{id}")
    public Solicitud buscarPorId(@PathVariable int id) {
        return solicitudService.obtenerPorId(id);
    }

    @PostMapping
    public Solicitud crear(@RequestBody SolicitudDTO dto) {
        return solicitudService.guardar(dto);
    }

    @PatchMapping("/{id}")
    public Solicitud actualizarEstado(@PathVariable int id, @RequestBody SolicitudDTO dto) {
        return solicitudService.actualizarEstado(id, dto);
    }

//    @PatchMapping("/{id}")
//    public Solicitud actualizarEstado(@PathVariable int id, @RequestBody SolicitudDTO dto) {
//        return solicitudService.actualizarEstado(id, dto);
//    }

//    @PatchMapping("/{id}")
//    public Solicitud actualizarCostoFinalYTiempoReal(@PathVariable int id, @RequestBody SolicitudDTO dto) {
//        return solicitudService.actualizarCostoFinalYTiempoReal(id, dto);
//    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        solicitudService.eliminar(id);
    }
}
