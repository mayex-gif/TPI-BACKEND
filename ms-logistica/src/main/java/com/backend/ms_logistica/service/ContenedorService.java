package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.ContenedorDTO;
import com.backend.ms_logistica.dto.EstadoDTO;
import com.backend.ms_logistica.model.AmbitoEstado;
import com.backend.ms_logistica.model.Contenedor;
import com.backend.ms_logistica.model.Estado;
import com.backend.ms_logistica.model.Solicitud;
import com.backend.ms_logistica.repository.ContenedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContenedorService {

    private final ContenedorRepository contenedorRepository;
    private final EstadoService estadoService;

    public ContenedorService(ContenedorRepository contenedorRepository,
                             EstadoService estadoService) {
        this.contenedorRepository = contenedorRepository;
        this.estadoService = estadoService;
    }

    // ======== OPERACIONES CRUD ========

    public List<ContenedorDTO> obtenerTodos() {
        return contenedorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ContenedorDTO obtenerPorId(Integer id) {
        Contenedor contenedor = contenedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado con ID: " + id));
        return toDTO(contenedor);
    }

    public ContenedorDTO obtenerPorSolicitud(Integer idSolicitud) {
        Contenedor contenedor = contenedorRepository.findBySolicitudIdSolicitud(idSolicitud)
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró contenedor para la solicitud: " + idSolicitud));
        return toDTO(contenedor);
    }

    public ContenedorDTO actualizar(Integer id, ContenedorDTO dto) {
        Contenedor contenedor = contenedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado con ID: " + id));

        contenedor.setPeso(dto.getPeso());
        contenedor.setUnidadPeso(dto.getUnidadPeso());
        contenedor.setVolumen(dto.getVolumen());

        if (dto.getEstado() != null && dto.getEstado().getIdEstado() != null) {
            Estado estado = estadoService.obtenerEstadoEntity(dto.getEstado().getIdEstado());
            contenedor.setEstado(estado);
        }

        Contenedor actualizado = contenedorRepository.save(contenedor);
        return toDTO(actualizado);
    }

    // ======== OPERACIONES DE NEGOCIO ========

    public ContenedorDTO cambiarEstado(Integer id, String nombreEstado) {
        Contenedor contenedor = contenedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado con ID: " + id));

        Estado nuevoEstado = estadoService.obtenerEstadoPorNombreYAmbito(
                nombreEstado, AmbitoEstado.CONTENEDOR);

        contenedor.setEstado(nuevoEstado);
        Contenedor actualizado = contenedorRepository.save(contenedor);

        return toDTO(actualizado);
    }

    public List<ContenedorDTO> obtenerPorEstado(String nombreEstado) {
        Estado estado = estadoService.obtenerEstadoPorNombreYAmbito(
                nombreEstado, AmbitoEstado.CONTENEDOR);

        return contenedorRepository.findByEstado(estado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Contenedor crearContenedorParaSolicitud(Solicitud solicitud, Double peso,
                                                   String unidadPeso, Double volumen) {
        Estado estadoInicial = estadoService.obtenerEstadoPorNombreYAmbito(
                "EN_ORIGEN", AmbitoEstado.CONTENEDOR);

        Contenedor contenedor = new Contenedor();
        contenedor.setSolicitud(solicitud);
        contenedor.setPeso(peso);
        contenedor.setUnidadPeso(com.backend.ms_logistica.model.UnidadPeso.valueOf(unidadPeso));
        contenedor.setVolumen(volumen);
        contenedor.setEstado(estadoInicial);

        return contenedorRepository.save(contenedor);
    }

    public Contenedor obtenerContenedorEntity(Integer id) {
        return contenedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado con ID: " + id));
    }

    // ======== MAPPERS ========

    private ContenedorDTO toDTO(Contenedor contenedor) {
        if (contenedor == null) return null;

        ContenedorDTO dto = new ContenedorDTO();
        dto.setIdContenedor(contenedor.getIdContenedor());
        dto.setIdSolicitud(contenedor.getSolicitud() != null ?
                contenedor.getSolicitud().getIdSolicitud() : null);
        dto.setPeso(contenedor.getPeso());
        dto.setUnidadPeso(contenedor.getUnidadPeso());
        dto.setVolumen(contenedor.getVolumen());
        dto.setEstado(estadoDTOFromEntity(contenedor.getEstado()));

        return dto;
    }

    private EstadoDTO estadoDTOFromEntity(Estado estado) {
        if (estado == null) return null;

        return new EstadoDTO(
                estado.getIdEstado(),
                estado.getAmbito(),
                estado.getNombre(),
                estado.getDescripcion()
        );
    }
}