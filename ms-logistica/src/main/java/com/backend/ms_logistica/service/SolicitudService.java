package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.SolicitudDTO;
import com.backend.ms_logistica.model.Solicitud;
import com.backend.ms_logistica.model.Contenedor;
import com.backend.ms_logistica.model.Estado;
import com.backend.ms_logistica.model.Ruta;
import com.backend.ms_logistica.repository.SolicitudRepository;
import com.backend.ms_logistica.repository.EstadoRepository;
import com.backend.ms_logistica.repository.RutaRepository;
import com.backend.ms_logistica.repository.ContenedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository repo;

    @Autowired
    private RutaRepository rutaRepo;

    @Autowired
    private EstadoRepository estadoRepo;

    @Autowired
    private ContenedorRepository contenedorRepo;

    public Solicitud crear(SolicitudDTO dto) {
        Solicitud s = new Solicitud();
        s.setIdCliente(dto.getIdCliente());
        s.setDescripcion(dto.getDescripcion());
        s.setCostoEstimado(dto.getCostoEstimado());
        s.setTiempoEstimado(dto.getTiempoEstimado());

        // Asignar estado desde la base
        s.setEstado(estadoRepo.findById(dto.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado")));

        // Asignar ruta si viene
        if (dto.getIdRuta() != null) {
            s.setRuta(rutaRepo.findById(dto.getIdRuta())
                    .orElseThrow(() -> new RuntimeException("Ruta no encontrada")));
        }

        // Contenedores
        if (dto.getContenedoresIds() != null) {
            List<Contenedor> contenedores = contenedorRepo.findAllById(dto.getContenedoresIds());
            contenedores.forEach(c -> c.setSolicitud(s));
            s.setContenedores(contenedores);
        }

        return repo.save(s);
    }

    public Solicitud buscar(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada: " + id));
    }

    public List<Solicitud> listar() {
        return repo.findAll();
    }

    public Solicitud actualizar(Integer id, SolicitudDTO dto) {
        Solicitud s = buscar(id);

        s.setIdCliente(dto.getIdCliente());

        if (dto.getIdRuta() != null) {
            s.setRuta(rutaRepo.findById(dto.getIdRuta()).orElseThrow(() -> new RuntimeException("Ruta no encontrada")));
        }

        s.setEstado(estadoRepo.findById(dto.getIdEstado()).orElseThrow(() -> new RuntimeException("Estado no encontrado")));
        s.setDescripcion(dto.getDescripcion());
        s.setCostoEstimado(dto.getCostoEstimado());
        s.setTiempoEstimado(dto.getTiempoEstimado());

        return repo.save(s);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // Mappers
    public Solicitud toEntity(SolicitudDTO dto) {
        Solicitud s = new Solicitud();
        s.setIdCliente(dto.getIdCliente());

        if(dto.getIdRuta() != null)
            s.setRuta(rutaRepo.findById(dto.getIdRuta())
                    .orElseThrow(() -> new RuntimeException("Ruta no encontrada: " + dto.getIdRuta())));

        s.setEstado(estadoRepo.findById(dto.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado: " + dto.getIdEstado())));

        s.setDescripcion(dto.getDescripcion());
        s.setCostoEstimado(dto.getCostoEstimado());
        s.setTiempoEstimado(dto.getTiempoEstimado());

        // Contenedores
        if(dto.getContenedoresIds() != null) {
            List<Contenedor> contenedores = dto.getContenedoresIds().stream()
                    .map(id -> contenedorRepo.findById(id)
                            .orElseThrow(() -> new RuntimeException("Contenedor no encontrado: " + id)))
                    .collect(Collectors.toList());
            s.setContenedores(contenedores);
        }

        return s;
    }

    public SolicitudDTO toDTO(Solicitud s) {
        SolicitudDTO dto = new SolicitudDTO();
        dto.setIdSolicitud(s.getIdSolicitud());
        dto.setIdCliente(s.getIdCliente());
        if(s.getRuta() != null) dto.setIdRuta(s.getRuta().getIdRuta());
        dto.setIdEstado(s.getEstado().getIdEstado());
        dto.setDescripcion(s.getDescripcion());
        dto.setCostoEstimado(s.getCostoEstimado());
        dto.setTiempoEstimado(s.getTiempoEstimado());
        dto.setContenedoresIds(s.getContenedores().stream()
                .map(Contenedor::getIdContenedor)
                .collect(Collectors.toList()));
        return dto;
    }
}
