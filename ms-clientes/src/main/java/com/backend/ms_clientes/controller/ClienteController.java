package com.backend.ms_clientes.controller;

import com.backend.ms_clientes.dto.ClienteDTO;
import com.backend.ms_clientes.model.Cliente;
import com.backend.ms_clientes.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v0.1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Listar: Requiere ADMINISTRADOR o CLIENTE
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','CLIENTE')")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        // Asume que clienteService::toDTO es un método estático o accesible que convierte Cliente a ClienteDTO.
        List<ClienteDTO> clientes = clienteService.listar().stream()
                .map(ClienteService::toDTO)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    // Buscar: Requiere ADMINISTRADOR o CLIENTE
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscar(@PathVariable Integer id) {
        Cliente c = clienteService.buscar(id);
        return ResponseEntity.ok(ClienteService.toDTO(c));
    }

    /**
     * Crea un nuevo cliente. Este endpoint suele ser público para auto-registro,
     * pero la seguridad de la aplicación lo define. Asume que el servicio
     * maneja la creación de usuario en Keycloak y la asignación del rol 'CLIENTE'.
     *
     * @param clienteDTO Los datos del nuevo cliente.
     * @return El ClienteDTO guardado.
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        // La llamada correcta es usar el DTO para permitir que el servicio:
        // 1. Cree usuario en Keycloak y asigne rol 'CLIENTE'.
        // 2. Guarde la entidad en la DB.
        ClienteDTO guardado = clienteService.crear(clienteDTO);
        return ResponseEntity.ok(guardado);
    }

    // Actualizar: Requiere ADMINISTRADOR
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Integer id,
                                                 @Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente c = ClienteService.toEntity(clienteDTO);
        Cliente actualizado = clienteService.actualizar(id, c);
        return ResponseEntity.ok(ClienteService.toDTO(actualizado));
    }

    // Eliminar: Requiere ADMINISTRADOR
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}