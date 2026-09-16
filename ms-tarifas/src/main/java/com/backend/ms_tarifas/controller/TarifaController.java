package com.backend.ms_tarifas.controller;

import com.backend.ms_tarifas.model.Tarifa;
import com.backend.ms_tarifas.service.TarifaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/tarifas")
public class TarifaController {

    private final TarifaService service;

    public TarifaController(TarifaService service) { this.service = service; }

    @GetMapping
    public List<Tarifa> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Tarifa create(@RequestBody Tarifa tarifa) {
        return service.create(tarifa);
    }
}
