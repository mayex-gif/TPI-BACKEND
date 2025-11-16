package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.UbicacionDTO;
import com.backend.ms_logistica.model.Ubicacion;
import com.backend.ms_logistica.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UbicacionService {

    @Autowired
    private UbicacionRepository repo;

    // Crear
    public Ubicacion crear(Ubicacion u) {
        return repo.save(u);
    }

    // Listar
    public List<Ubicacion> listar() {
        return repo.findAll();
    }

    // Buscar
    public Ubicacion buscar(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada: " + id));
    }

    // Actualizar
    public Ubicacion actualizar(Integer id, Ubicacion nuevo) {
        Ubicacion u = buscar(id);
        u.setNombre(nuevo.getNombre());
        u.setDireccion(nuevo.getDireccion());
        u.setLatitud(nuevo.getLatitud());
        u.setLongitud(nuevo.getLongitud());
        return repo.save(u);
    }

    // Eliminar
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // Mapper: Entity -> DTO
    public static UbicacionDTO toDTO(Ubicacion u) {
        return new UbicacionDTO(
                u.getIdUbicacion(),
                u.getNombre(),
                u.getDireccion(),
                u.getLatitud(),
                u.getLongitud()
        );
    }

    // Mapper: DTO -> Entity
    public static Ubicacion toEntity(UbicacionDTO dto) {
        Ubicacion u = new Ubicacion();
        u.setNombre(dto.getNombre());
        u.setDireccion(dto.getDireccion());
        u.setLatitud(dto.getLatitud());
        u.setLongitud(dto.getLongitud());
        return u;
    }

    // List DTO
    public List<UbicacionDTO> listarDTO() {
        return listar().stream()
                .map(UbicacionService::toDTO)
                .collect(Collectors.toList());
    }
}
