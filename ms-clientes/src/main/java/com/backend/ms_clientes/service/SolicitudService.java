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
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final ClienteRepository clienteRepository;
    private final ContenedorRepository contenedorRepository;
    private final EstadoRepository estadoRepository;

    public SolicitudService(SolicitudRepository solicitudRepository, ClienteRepository clienteRepository
            , ContenedorRepository contenedorRepository, EstadoRepository estadoRepository
    ) {
        this.solicitudRepository = solicitudRepository;
        this.clienteRepository = clienteRepository;
        this.contenedorRepository = contenedorRepository;
        this.estadoRepository = estadoRepository;
    }

    public List<Solicitud> listarTodos() {
        return solicitudRepository.findAll();
    }

    public Solicitud obtenerPorId(Integer id) {
        return solicitudRepository.findById(id).orElse(null);
    }

    public Solicitud guardar(SolicitudDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getId_cliente()).orElseThrow();
        Contenedor contenedor = contenedorRepository.findById(dto.getId_contenedor()).orElseThrow();
        Estado estado = estadoRepository.findById(dto.getId_estado()).orElseThrow();

        Solicitud solicitud = new Solicitud(cliente, contenedor, estado, dto.getCosto_estimado()
                , dto.getTiempo_estimado(), dto.getCosto_final(), dto.getTiempo_real());
        return solicitudRepository.save(solicitud);
    }

    public void eliminar(Integer id) {
        solicitudRepository.deleteById(id);
    }

    public Solicitud actualizarEstado(Integer id_Solicitud, SolicitudDTO dto) {
        Solicitud solicitud = solicitudRepository.findById(id_Solicitud).orElseThrow();
        Estado estado = estadoRepository.findById(dto.getId_estado()).orElseThrow();

        solicitud.setEstado(estado);
        return solicitudRepository.save(solicitud);
    }

    public Solicitud actualizarCostoFinalYTiempoReal(Integer id_Solicitud, SolicitudDTO dto) {
        Solicitud solicitud = solicitudRepository.findById(id_Solicitud).orElseThrow();
        Double costoFinal = dto.getCosto_final();
        Double tiempoReal = dto.getTiempo_real();

        solicitud.setCosto_estimado(costoFinal);
        solicitud.setTiempo_estimado(tiempoReal);
        return solicitudRepository.save(solicitud);
    }
}
