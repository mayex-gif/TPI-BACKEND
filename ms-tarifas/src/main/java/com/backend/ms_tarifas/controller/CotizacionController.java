package com.backend.ms_tarifas.controller;

import com.backend.ms_tarifas.model.Cotizacion;
import com.backend.ms_tarifas.service.CotizacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/cotizaciones")
public class CotizacionController {

    private final CotizacionService service;

    public CotizacionController(CotizacionService service) { this.service = service; }

    @GetMapping
    public List<Cotizacion> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Cotizacion create(@RequestBody Cotizacion cot) {
        return service.create(cot);
    }
}
