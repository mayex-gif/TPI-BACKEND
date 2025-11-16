package com.backend.ms_clientes.controller;

import com.backend.ms_clientes.dto.ClienteDTO;
import com.backend.ms_clientes.model.Cliente;
import com.backend.ms_clientes.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v0.1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        List<ClienteDTO> clientes = clienteService.listar().stream()
                .map(ClienteService::toDTO)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscar(@PathVariable Integer id) {
        Cliente c = clienteService.buscar(id);
        return ResponseEntity.ok(ClienteService.toDTO(c));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.toEntity(clienteDTO);
        Cliente guardado = clienteService.crear(cliente);
        return ResponseEntity.ok(clienteService.toDTO(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Integer id,
                                                 @Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente c = ClienteService.toEntity(clienteDTO);
        Cliente actualizado = clienteService.actualizar(id, c);
        return ResponseEntity.ok(ClienteService.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
