package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.ContenedorDTO;
import com.backend.ms_logistica.model.Contenedor;
import com.backend.ms_logistica.model.Solicitud;
import com.backend.ms_logistica.repository.ContenedorRepository;
import com.backend.ms_logistica.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContenedorService {

    @Autowired
    private ContenedorRepository repo;

    @Autowired
    private SolicitudRepository solicitudRepo;

    // Crear
    public Contenedor crear(Contenedor c) {
        return repo.save(c);
    }

    // Listar
    public List<Contenedor> listar() {
        return repo.findAll();
    }

    // Buscar
    public Contenedor buscar(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado: " + id));
    }

    // Actualizar
    public Contenedor actualizar(Integer id, Contenedor nuevo) {
        Contenedor c = buscar(id);
        c.setTipo(nuevo.getTipo());
        c.setVolumen(nuevo.getVolumen());
        c.setPeso(nuevo.getPeso());
        c.setSolicitud(nuevo.getSolicitud());
        return repo.save(c);
    }

    // Eliminar
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // Mapper: Entity -> DTO
    public ContenedorDTO toDTO(Contenedor c) {
        return new ContenedorDTO(
                c.getIdContenedor(),
                c.getSolicitud().getIdSolicitud(),
                c.getTipo(),
                c.getVolumen(),
                c.getPeso()
        );
    }

    // Mapper: DTO -> Entity
    public Contenedor toEntity(ContenedorDTO dto) {
        Solicitud solicitud = solicitudRepo.findById(dto.getIdSolicitud())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada: " + dto.getIdSolicitud()));
        Contenedor c = new Contenedor();
        c.setSolicitud(solicitud);
        c.setTipo(dto.getTipo());
        c.setVolumen(dto.getVolumen());
        c.setPeso(dto.getPeso());
        return c;
    }

    // List DTO
    public List<ContenedorDTO> listarDTO() {
        return listar().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
