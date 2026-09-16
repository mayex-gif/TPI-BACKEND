package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.CamionDTO;
import com.backend.ms_logistica.model.Camion;
import com.backend.ms_logistica.repository.CamionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CamionService {

    private final CamionRepository camionRepository;

    public CamionService(CamionRepository camionRepository) {
        this.camionRepository = camionRepository;
    }

    // ======== OPERACIONES CRUD ========

    public List<CamionDTO> obtenerTodos() {
        return camionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CamionDTO obtenerPorPatente(String patente) {
        Camion camion = camionRepository.findById(patente)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado con patente: " + patente));
        return toDTO(camion);
    }

    public CamionDTO crear(CamionDTO dto) {
        if (camionRepository.existsById(dto.getPatente())) {
            throw new RuntimeException("Ya existe un camión con patente: " + dto.getPatente());
        }

        Camion camion = toEntity(dto);
        Camion guardado = camionRepository.save(camion);
        return toDTO(guardado);
    }

    public CamionDTO actualizar(String patente, CamionDTO dto) {
        Camion camion = camionRepository.findById(patente)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado con patente: " + patente));

        camion.setNombreTransportista(dto.getNombreTransportista());
        camion.setTelefono(dto.getTelefono());
        camion.setCapacidadPeso(dto.getCapacidadPeso());
        camion.setCapacidadVolumen(dto.getCapacidadVolumen());
        camion.setDisponible(dto.getDisponible());
        camion.setCostoBaseKm(dto.getCostoBaseKm());
        camion.setConsumoCombustible(dto.getConsumoCombustible());

        Camion actualizado = camionRepository.save(camion);
        return toDTO(actualizado);
    }

    public void eliminar(String patente) {
        if (!camionRepository.existsById(patente)) {
            throw new RuntimeException("Camión no encontrado con patente: " + patente);
        }
        camionRepository.deleteById(patente);
    }

    // ======== OPERACIONES DE NEGOCIO ========

    public List<CamionDTO> obtenerDisponibles() {
        return camionRepository.findByDisponible(true).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CamionDTO> buscarCompatibles(Double pesoRequerido, Double volumenRequerido) {
        return camionRepository.findByDisponible(true).stream()
                .filter(c -> c.getCapacidadPeso() >= pesoRequerido &&
                        c.getCapacidadVolumen() >= volumenRequerido)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void marcarComoOcupado(String patente) {
        Camion camion = camionRepository.findById(patente)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado con patente: " + patente));
        camion.setDisponible(false);
        camionRepository.save(camion);
    }

    public void marcarComoDisponible(String patente) {
        Camion camion = camionRepository.findById(patente)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado con patente: " + patente));
        camion.setDisponible(true);
        camionRepository.save(camion);
    }

    public Camion obtenerCamionEntity(String patente) {
        return camionRepository.findById(patente)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado con patente: " + patente));
    }

    public boolean puedeTransportar(String patente, Double peso, Double volumen) {
        Camion camion = obtenerCamionEntity(patente);
        return camion.puedeTransportar(peso, volumen);
    }

    // ======== MAPPERS ========

    private CamionDTO toDTO(Camion camion) {
        if (camion == null) return null;

        return new CamionDTO(
                camion.getPatente(),
                camion.getNombreTransportista(),
                camion.getTelefono(),
                camion.getCapacidadPeso(),
                camion.getCapacidadVolumen(),
                camion.getDisponible(),
                camion.getCostoBaseKm(),
                camion.getConsumoCombustible()
        );
    }

    private Camion toEntity(CamionDTO dto) {
        if (dto == null) return null;

        Camion camion = new Camion();
        camion.setPatente(dto.getPatente());
        camion.setNombreTransportista(dto.getNombreTransportista());
        camion.setTelefono(dto.getTelefono());
        camion.setCapacidadPeso(dto.getCapacidadPeso());
        camion.setCapacidadVolumen(dto.getCapacidadVolumen());
        camion.setDisponible(dto.getDisponible() != null ? dto.getDisponible() : true);
        camion.setCostoBaseKm(dto.getCostoBaseKm());
        camion.setConsumoCombustible(dto.getConsumoCombustible());

        return camion;
    }
}
