package com.backend.ms_clientes.service;

import com.backend.ms_clientes.dto.SolicitudDTO;
import com.backend.ms_clientes.model.Cliente;
import com.backend.ms_clientes.model.Contenedor;
import com.backend.ms_clientes.model.Estado;
import com.backend.ms_clientes.model.Solicitud;
import com.backend.ms_clientes.repository.ClienteRepository;
import com.backend.ms_clientes.repository.ContenedorRepository;
import com.backend.ms_clientes.repository.EstadoRepository;
import com.backend.ms_clientes.repository.SolicitudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final ClienteRepository clienteRepository;
    private final ContenedorRepository contenedorRepository;
    private final EstadoRepository estadoRepository;

    public SolicitudService(SolicitudRepository solicitudRepository,
                            ClienteRepository clienteRepository,
                            ContenedorRepository contenedorRepository,
                            EstadoRepository estadoRepository) {
        this.solicitudRepository = solicitudRepository;
        this.clienteRepository = clienteRepository;
        this.contenedorRepository = contenedorRepository;
        this.estadoRepository = estadoRepository;
    }

    public List<Solicitud> listarTodos() {
        return solicitudRepository.findAll();
    }

    public Solicitud obtenerPorId(Integer id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));
    }

    public Solicitud guardar(SolicitudDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getId_cliente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        Contenedor contenedor = contenedorRepository.findById(dto.getId_contenedor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contenedor no encontrado"));

        Estado estado = estadoRepository.findById(dto.getId_estado())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado no encontrado"));

        Solicitud solicitud = new Solicitud(
                cliente,
                contenedor,
                estado,
                dto.getCosto_estimado(),
                dto.getTiempo_estimado(),
                dto.getCosto_final(),
                dto.getTiempo_real()
        );

        return solicitudRepository.save(solicitud);
    }

    public void eliminar(Integer id) {
        if (!solicitudRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada");
        solicitudRepository.deleteById(id);
    }

    public Solicitud actualizarEstado(Integer idSolicitud, SolicitudDTO dto) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

        Estado estado = estadoRepository.findById(dto.getId_estado())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado no encontrado"));

        solicitud.setEstado(estado);
        return solicitudRepository.save(solicitud);
    }

    public Solicitud actualizarCostoFinalYTiempoReal(Integer idSolicitud, SolicitudDTO dto) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

        if (dto.getCosto_final() != null) solicitud.setCosto_final(dto.getCosto_final());
        if (dto.getTiempo_real() != null) solicitud.setTiempo_real(dto.getTiempo_real());

        return solicitudRepository.save(solicitud);
    }
}
