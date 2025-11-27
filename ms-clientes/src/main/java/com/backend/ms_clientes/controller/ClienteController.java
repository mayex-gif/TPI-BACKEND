package com.backend.ms_clientes.controller;

import com.backend.ms_clientes.dto.ClienteDTO;
import com.backend.ms_clientes.model.Cliente;
import com.backend.ms_clientes.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // <-- IMPORTAR
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Listar: Requiere CLIENTES_READER o superiores
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','CLIENTE')")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        List<ClienteDTO> clientes = clienteService.listar().stream()
                .map(ClienteService::toDTO)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    // Buscar: Requiere CLIENTES_READER o superiores
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscar(@PathVariable Integer id) {
        Cliente c = clienteService.buscar(id);
        return ResponseEntity.ok(ClienteService.toDTO(c));
    }

    // Crear: Requiere CLIENTE o ADMINISTRADOR (Si es para uso interno del operador)
    // NOTA: Si este endpoint es usado por el registro público, DEBES EXCLUIRLO o cambiar la ruta
    @PreAuthorize("hasAnyRole('CLIENTE','ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        // NOTA: El servicio debe encargarse de asignar el rol 'CLIENTE' en Keycloak
        // y crear el registro de cliente en la base de datos.
        Cliente cliente = clienteService.toEntity(clienteDTO);
        Cliente guardado = clienteService.crear(cliente);
        return ResponseEntity.ok(clienteService.toDTO(guardado));
    }

    // Actualizar: Requiere CLIENTES_MANAGER o superiores
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Integer id,
                                                 @Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente c = ClienteService.toEntity(clienteDTO);
        Cliente actualizado = clienteService.actualizar(id, c);
        return ResponseEntity.ok(ClienteService.toDTO(actualizado));
    }

    // Eliminar: Requiere solo ADMINISTRADOR
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}