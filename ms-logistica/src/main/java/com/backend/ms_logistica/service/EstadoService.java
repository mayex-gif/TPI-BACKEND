package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.EstadoDTO;
import com.backend.ms_logistica.model.AmbitoEstado;
import com.backend.ms_logistica.model.Estado;
import com.backend.ms_logistica.repository.EstadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    // ======== OPERACIONES CRUD ========

    public List<EstadoDTO> obtenerTodos() {
        return estadoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EstadoDTO> obtenerPorAmbito(AmbitoEstado ambito) {
        return estadoRepository.findByAmbito(ambito).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EstadoDTO obtenerPorId(Integer id) {
        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));
        return toDTO(estado);
    }

    public EstadoDTO crear(EstadoDTO dto) {
        Estado estado = toEntity(dto);
        Estado guardado = estadoRepository.save(estado);
        return toDTO(guardado);
    }

    public EstadoDTO actualizar(Integer id, EstadoDTO dto) {
        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));

        estado.setAmbito(dto.getAmbito());
        estado.setNombre(dto.getNombre());
        estado.setDescripcion(dto.getDescripcion());

        Estado actualizado = estadoRepository.save(estado);
        return toDTO(actualizado);
    }

    public void eliminar(Integer id) {
        if (!estadoRepository.existsById(id)) {
            throw new RuntimeException("Estado no encontrado con ID: " + id);
        }
        estadoRepository.deleteById(id);
    }

    // ======== MÉTODOS DE UTILIDAD ========

    public Estado obtenerEstadoEntity(Integer id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));
    }

    public Estado obtenerEstadoPorNombreYAmbito(String nombre, AmbitoEstado ambito) {
        return estadoRepository.findByNombreAndAmbito(nombre, ambito)
                .orElseThrow(() -> new RuntimeException(
                        "Estado no encontrado: " + nombre + " en ámbito " + ambito));
    }

    // ======== MAPPERS ========

    private EstadoDTO toDTO(Estado estado) {
        if (estado == null) return null;

        return new EstadoDTO(
                estado.getIdEstado(),
                estado.getAmbito(),
                estado.getNombre(),
                estado.getDescripcion()
        );
    }

    private Estado toEntity(EstadoDTO dto) {
        if (dto == null) return null;

        Estado estado = new Estado();
        estado.setIdEstado(dto.getIdEstado());
        estado.setAmbito(dto.getAmbito());
        estado.setNombre(dto.getNombre());
        estado.setDescripcion(dto.getDescripcion());

        return estado;
    }
}
