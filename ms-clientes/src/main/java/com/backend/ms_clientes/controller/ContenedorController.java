package com.backend.ms_clientes.controller;

import com.backend.ms_clientes.dto.ContenedorDTO;
import com.backend.ms_clientes.model.Cliente;
import com.backend.ms_clientes.model.Contenedor;
import com.backend.ms_clientes.model.Estado;
import com.backend.ms_clientes.service.ContenedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v0.1/contenedores")
public class ContenedorController {

    private final ContenedorService contenedorService;

    public ContenedorController(ContenedorService contenedorService) {
        this.contenedorService = contenedorService;
    }

    @GetMapping
    public ResponseEntity<List<ContenedorDTO>> listarTodos() {
        List<ContenedorDTO> dtos = contenedorService.listarTodos()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContenedorDTO> obtenerPorId(@PathVariable Integer id) {
        Contenedor contenedor = contenedorService.obtenerPorId(id);
        if (contenedor == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDTO(contenedor));
    }

    @PostMapping
    public ResponseEntity<ContenedorDTO> crear(@RequestBody ContenedorDTO dto) {
        Estado estado = contenedorService.getEstadoById(dto.getId_estado());
        Cliente cliente = contenedorService.getClienteById(dto.getId_cliente());

        Contenedor.Volumen volumenEnum;
        try {
            volumenEnum = Contenedor.Volumen.valueOf(dto.getVolumen().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Volumen inválido. Valores permitidos: KILOGRAMO, TONELADA, LIBRA");
        }

        Contenedor contenedor = new Contenedor(dto.getPeso(), volumenEnum, estado, cliente);
        Contenedor guardado = contenedorService.guardar(contenedor);

        return ResponseEntity.ok(toDTO(guardado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContenedorDTO> actualizarEstado(@PathVariable Integer id, @RequestBody ContenedorDTO dto) {
        Contenedor contenedor = contenedorService.obtenerPorId(id);
        if (contenedor == null) return ResponseEntity.notFound().build();

        Estado nuevoEstado = contenedorService.getEstadoById(dto.getId_estado());
        contenedor.setEstado(nuevoEstado);

        Contenedor actualizado = contenedorService.guardar(contenedor);
        return ResponseEntity.ok(toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        contenedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // --- Conversión manual sin mapper ---
    private ContenedorDTO toDTO(Contenedor c) {
        ContenedorDTO dto = new ContenedorDTO();
        dto.setId(c.getId_contenedor());
        dto.setPeso(c.getPeso());
        dto.setVolumen(c.getVolumen().name());
        dto.setEstado(c.getEstado() != null ? c.getEstado().getDescripcion() : null);
        dto.setCliente(c.getCliente() != null ? c.getCliente().getNombre() + " " + c.getCliente().getApellido() : null);
        return dto;
    }
}
