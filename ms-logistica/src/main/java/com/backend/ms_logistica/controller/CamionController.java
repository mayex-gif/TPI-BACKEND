package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.model.Camion;
import com.backend.ms_logistica.service.CamionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/camiones")
public class CamionController {

    private final CamionService camionService;

    public CamionController(CamionService camionService) {
        this.camionService = camionService;
    }

    @GetMapping
    public List<Camion> listarTodos() {
        return camionService.listarTodos();
    }
}
