package com.backend.ms_clientes.controller;

import com.backend.ms_clientes.dto.ContenedorDTO;
import com.backend.ms_clientes.model.Contenedor;
import com.backend.ms_clientes.service.ContenedorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/contenedores")
public class ContenedorController {

    private final ContenedorService contenedorService;

    public ContenedorController(ContenedorService contenedorService) {
        this.contenedorService = contenedorService;
    }

    @GetMapping
    public List<Contenedor> listarTodos() {
        return contenedorService.listarTodos();
    }

    @GetMapping("/{id}")
    public Contenedor obtenerPorId(@PathVariable Integer id) {
        return contenedorService.obtenerPorId(id);
    }

    @PostMapping
    public Contenedor crear(@RequestBody ContenedorDTO dto) {
        return contenedorService.guardar(dto);
    }

    @PatchMapping("/{id}")
    public Contenedor actualizarEstado(@PathVariable Integer id, @RequestBody ContenedorDTO dto) {
        return contenedorService.actualizarEstado(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        contenedorService.eliminar(id);
    }
}


