package com.backend.ms_clientes.controller;

import com.backend.ms_clientes.model.Estado;
import com.backend.ms_clientes.service.EstadoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public List<Estado> listarTodos() {
        return estadoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Estado obtenerPorId(@PathVariable Integer id) {
        return estadoService.obtenerPorId(id);
    }

}


