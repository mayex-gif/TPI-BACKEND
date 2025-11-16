package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.EstadoDTO;
import com.backend.ms_logistica.model.Estado;
import com.backend.ms_logistica.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repo;

    // Crear
    public Estado crear(Estado estado) {
        return repo.save(estado);
    }

    // Listar
    public List<Estado> listar() {
        return repo.findAll();
    }

    // Buscar
    public Estado buscar(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado: " + id));
    }

    // Actualizar
    public Estado actualizar(Integer id, Estado nuevo) {
        Estado e = buscar(id);
        e.setNombre(nuevo.getNombre());
        e.setDescripcion(nuevo.getDescripcion());
        return repo.save(e);
    }

    // Eliminar
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // Mapper: Entity -> DTO
    public static EstadoDTO toDTO(Estado estado) {
        return new EstadoDTO(
                estado.getIdEstado(),
                estado.getNombre(),
                estado.getDescripcion()
        );
    }

    // Mapper: DTO -> Entity
    public static Estado toEntity(EstadoDTO dto) {
        Estado e = new Estado();
        e.setNombre(dto.getNombre());
        e.setDescripcion(dto.getDescripcion());
        return e;
    }

    // List DTO
    public List<EstadoDTO> listarDTO() {
        return listar().stream()
                .map(EstadoService::toDTO)
                .collect(Collectors.toList());
    }
}
