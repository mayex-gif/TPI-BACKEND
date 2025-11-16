package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.CamionDTO;
import com.backend.ms_logistica.model.Camion;
import com.backend.ms_logistica.repository.CamionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CamionService {

    @Autowired
    private CamionRepository repo;

    public Camion crear(Camion camion) {
        return repo.save(camion);
    }

    public Camion buscar(String patente) {
        return repo.findById(patente)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado: " + patente));
    }

    public List<Camion> listar() {
        return repo.findAll();
    }

    public Camion actualizar(String patente, Camion nuevo) {
        Camion c = buscar(patente);
        c.setNombreTransportista(nuevo.getNombreTransportista());
        c.setTelefono(nuevo.getTelefono());
        c.setCapacidadPeso(nuevo.getCapacidadPeso());
        c.setCapacidadVolumen(nuevo.getCapacidadVolumen());
        c.setDisponible(nuevo.getDisponible());
        c.setCostoBaseKm(nuevo.getCostoBaseKm());
        c.setConsumoCombustible(nuevo.getConsumoCombustible());
        return repo.save(c);
    }

    public void eliminar(String patente) {
        repo.deleteById(patente);
    }

    public static CamionDTO toDTO(Camion camion) {
        CamionDTO dto = new CamionDTO();
        dto.setPatente(camion.getPatente());
        dto.setNombreTransportista(camion.getNombreTransportista());
        dto.setTelefono(camion.getTelefono());
        dto.setCapacidadPeso(camion.getCapacidadPeso());
        dto.setCapacidadVolumen(camion.getCapacidadVolumen());
        dto.setDisponible(camion.getDisponible());
        dto.setCostoBaseKm(camion.getCostoBaseKm());
        dto.setConsumoCombustible(camion.getConsumoCombustible());
        return dto;
    }

    public static Camion toEntity(CamionDTO dto) {
        Camion c = new Camion();
        c.setPatente(dto.getPatente());
        c.setNombreTransportista(dto.getNombreTransportista());
        c.setTelefono(dto.getTelefono());
        c.setCapacidadPeso(dto.getCapacidadPeso());
        c.setCapacidadVolumen(dto.getCapacidadVolumen());
        c.setDisponible(dto.getDisponible());
        c.setCostoBaseKm(dto.getCostoBaseKm());
        c.setConsumoCombustible(dto.getConsumoCombustible());
        return c;
    }
}
