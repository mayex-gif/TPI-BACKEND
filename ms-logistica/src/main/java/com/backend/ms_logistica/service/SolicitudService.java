package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.ContenedorDTO;
import com.backend.ms_logistica.dto.SolicitudDTO;
import com.backend.ms_logistica.model.Solicitud;
import com.backend.ms_logistica.model.Contenedor;
import com.backend.ms_logistica.model.Estado;
import com.backend.ms_logistica.model.Ruta;
import com.backend.ms_logistica.remoto.ClienteDTO;
import com.backend.ms_logistica.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudService {

    @Autowired
    private final SolicitudRepository solicitudRepo;
    @Autowired
    private final ContenedorRepository contenedorRepo;
    @Autowired
    private final RutaRepository rutaRepo;
    @Autowired
    private final EstadoRepository estadoRepo;
    @Autowired
    private final RestTemplate restTemplate; // o WebClient
    @Autowired
    private final CamionRepository camionRepo;

    public SolicitudService(SolicitudRepository solicitudRepo,
                            ContenedorRepository contenedorRepo,
                            RutaRepository rutaRepo,
                            EstadoRepository estadoRepo,
                            CamionRepository camionRepo,
                            RestTemplate restTemplate) {
        this.solicitudRepo = solicitudRepo;
        this.contenedorRepo = contenedorRepo;
        this.rutaRepo = rutaRepo;
        this.estadoRepo = estadoRepo;
        this.camionRepo = camionRepo;
        this.restTemplate = restTemplate;
    }

    public Solicitud crear(SolicitudDTO solicitudDTO) {

        Integer clienteId = existeCliente(solicitudDTO.getCliente(), solicitudDTO.getIdCliente());

        Solicitud solicitud = new Solicitud();
        solicitud.setIdCliente(clienteId);
        solicitud.setDescripcion(solicitudDTO.getDescripcion());
        Estado estado = estadoRepo.findById(solicitudDTO.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado con ID " + solicitudDTO.getIdEstado() + " no encontrado."));

        solicitud.setEstado(estado);
        solicitud.setCostoEstimado(solicitudDTO.getCostoEstimado());
        solicitud.setTiempoEstimado(solicitudDTO.getTiempoEstimado());

        Ruta rutaAsignada = null;
        if (solicitudDTO.getIdRuta() != null) {
            rutaAsignada = rutaRepo.findById(solicitudDTO.getIdRuta()).get();
        }
        solicitud.setRuta(rutaAsignada);

        Solicitud saved = solicitudRepo.save(solicitud);
        return saved;
    }

    public Integer existeCliente(ClienteDTO clienteDTO, Integer clienteId) {

        // 1) Si viene un ID, simplemente verifico si existe
        if (clienteId != null) {
            try {
                restTemplate.getForEntity(
                        "http://localhost:8081/api/v0.1/clientes/" + clienteId,
                        Void.class
                );
                return clienteId; // existe → usarlo
            } catch (HttpClientErrorException.NotFound e) {
                // no existe → seguimos abajo para crearlo
            }
        }

        // 2) Si no hay ID, NECESITO datos para crearlo
        if (clienteDTO == null) {
            throw new RuntimeException("Faltan datos del cliente para crearlo.");
        }

        // 3) Crear cliente remotamente
        ClienteDTO nuevo = restTemplate.postForObject(
                "http://localhost:8081/api/v0.1/clientes",
                clienteDTO,
                ClienteDTO.class
        );

        if (nuevo == null || nuevo.getId() == null) {
            throw new RuntimeException("No se pudo crear el cliente.");
        }

        return nuevo.getId();
    }

    public Solicitud crear1(SolicitudDTO dto) {
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

        return solicitudRepo.save(s);
    }

    public Solicitud buscar(Integer id) {
        return solicitudRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada: " + id));
    }

    public List<Solicitud> listar() {
        return solicitudRepo.findAll();
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

        return solicitudRepo.save(s);
    }

    public void eliminar(Integer id) {
        solicitudRepo.deleteById(id);
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
