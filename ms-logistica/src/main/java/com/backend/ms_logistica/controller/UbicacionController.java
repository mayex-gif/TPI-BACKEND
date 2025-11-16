package com.backend.ms_logistica.controller;

import com.backend.ms_logistica.dto.UbicacionDTO;
import com.backend.ms_logistica.model.Ubicacion;
import com.backend.ms_logistica.service.UbicacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.1/ubicaciones")
public class UbicacionController {

    @Autowired
    private UbicacionService service;

    @GetMapping
    public ResponseEntity<List<UbicacionDTO>> listar() {
        return ResponseEntity.ok(service.listarDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UbicacionDTO> buscar(@PathVariable Integer id) {
        Ubicacion u = service.buscar(id);
        return ResponseEntity.ok(UbicacionService.toDTO(u));
    }

    @PostMapping
    public ResponseEntity<UbicacionDTO> crear(@Valid @RequestBody UbicacionDTO dto) {
        Ubicacion u = UbicacionService.toEntity(dto);
        Ubicacion creado = service.crear(u);
        return ResponseEntity.ok(UbicacionService.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UbicacionDTO> actualizar(@PathVariable Integer id,
                                                   @Valid @RequestBody UbicacionDTO dto) {
        Ubicacion u = UbicacionService.toEntity(dto);
        Ubicacion actualizado = service.actualizar(id, u);
        return ResponseEntity.ok(UbicacionService.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
