package com.backend.ms_logistica.service;


import com.backend.ms_logistica.dto.RutaDTO;
import com.backend.ms_logistica.model.Ruta;
import com.backend.ms_logistica.remoto.SolicitudDTO;
import com.backend.ms_logistica.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RutaService {

    private final RestTemplate restTemplate;
    private final RutaRepository rutaRepository;

    @Autowired
    public RutaService(RestTemplate restTemplate, RutaRepository rutaRepository) {
        this.restTemplate = restTemplate;
        this.rutaRepository = rutaRepository;
    }

    public List<Ruta> listarTodos() {
        return rutaRepository.findAll();
    }

    public List<Ruta> obtenerPorIdSolicitud(Integer id_solicitud) {
        // Opcional, si querés verificar que la solicitud realmente exista en ms-solicitudes
        SolicitudDTO solicitud = obtenerIdSolicitud(id_solicitud);
        if (solicitud == null) {
            throw new RuntimeException("No existe la solicitud con ID " + id_solicitud);
        }

        return rutaRepository.findByIdSolicitud(id_solicitud);
    }

    public Ruta obtenerPorId(Integer id) {
        return rutaRepository.findById(id).orElse(null);
    }

    public Ruta crearRutaConSolicitud(RutaDTO dto){
        SolicitudDTO solicitud = obtenerIdSolicitud(dto.getId_solicitud());
        // acá podrías validar si la solicitud existe o está activa
        Ruta ruta = new Ruta(
                dto.getCantidad_tramos(),
                dto.getCantidad_depositos(),
                dto.getDistance_total(),
                solicitud.getId_solicitud()
        );
        return rutaRepository.save(ruta);
    }

    public SolicitudDTO obtenerIdSolicitud(Integer id_solicitud) {
        String url = "http://localhost:8081/api/v0.1/solicitudes/" + id_solicitud;
        SolicitudDTO dto = restTemplate.getForObject(url, SolicitudDTO.class);
        if (dto == null) {
            throw new RuntimeException("No se encontró la solicitud con ID " + id_solicitud);
        }
        return dto;
    }

}
