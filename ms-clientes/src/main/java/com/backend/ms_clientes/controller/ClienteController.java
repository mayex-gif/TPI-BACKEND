package com.backend.ms_clientes.controller;

import com.backend.ms_clientes.dto.ClienteDTO;
import com.backend.ms_clientes.model.Cliente;
import com.backend.ms_clientes.service.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public Cliente obtenerPorId(@PathVariable Integer id) {
        return clienteService.obtenerPorId(id);
    }

    @PostMapping
    public Cliente crear(@RequestBody ClienteDTO dto) {
        return clienteService.guardar(dto);
    }

    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Integer id, @RequestBody ClienteDTO dto) {
        Cliente existente = clienteService.obtenerPorId(id);
        if (existente == null) return null; // o lanzar excepción 404
        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setEmail(dto.getEmail());
        existente.setTelefono(dto.getTelefono());
        return clienteService.guardar(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
    }
}

