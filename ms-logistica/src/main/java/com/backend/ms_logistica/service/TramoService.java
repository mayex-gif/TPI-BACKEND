package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.*;
import com.backend.ms_logistica.model.*;
import com.backend.ms_logistica.repository.TramoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TramoService {

    private final TramoRepository tramoRepository;
    private final EstadoService estadoService;
    private final DepositoService depositoService;
    private final CamionService camionService;

    public TramoService(TramoRepository tramoRepository, EstadoService estadoService,
                        DepositoService depositoService, CamionService camionService) {
        this.tramoRepository = tramoRepository;
        this.estadoService = estadoService;
        this.depositoService = depositoService;
        this.camionService = camionService;
    }

    // ======== OPERACIONES CRUD ========

    public List<TramoDTO> obtenerTodos() {
        return tramoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TramoDTO obtenerPorId(Integer id) {
        Tramo tramo = tramoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado con ID: " + id));
        return toDTO(tramo);
    }

    public List<TramoDTO> obtenerPorRuta(Integer idRuta) {
        return tramoRepository.findByRutaIdRuta(idRuta).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ======== OPERACIONES DE NEGOCIO ========

    public TramoDTO asignarCamion(Integer idTramo, String patenteCamion) {
        Tramo tramo = tramoRepository.findById(idTramo)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado con ID: " + idTramo));

        Camion camion = camionService.obtenerCamionEntity(patenteCamion);

        if (!camion.getDisponible()) {
            throw new RuntimeException("El camión " + patenteCamion + " no está disponible");
        }

        tramo.setCamion(camion);

        // Cambiar estado del tramo a ASIGNADO
        Estado estadoAsignado = estadoService.obtenerEstadoPorNombreYAmbito(
                "ASIGNADO", AmbitoEstado.TRAMO);
        tramo.setEstado(estadoAsignado);

        Tramo actualizado = tramoRepository.save(tramo);
        return toDTO(actualizado);
    }

    public TramoDTO iniciarTramo(Integer idTramo) {
        Tramo tramo = tramoRepository.findById(idTramo)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado con ID: " + idTramo));

        if (tramo.getCamion() == null) {
            throw new RuntimeException("No se puede iniciar un tramo sin camión asignado");
        }

        tramo.setFechaHoraInicio(LocalDateTime.now());

        Estado estadoIniciado = estadoService.obtenerEstadoPorNombreYAmbito(
                "INICIADO", AmbitoEstado.TRAMO);
        tramo.setEstado(estadoIniciado);

        // Marcar camión como ocupado
        camionService.marcarComoOcupado(tramo.getCamion().getPatente());

        Tramo actualizado = tramoRepository.save(tramo);
        return toDTO(actualizado);
    }

    public TramoDTO finalizarTramo(Integer idTramo) {
        Tramo tramo = tramoRepository.findById(idTramo)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado con ID: " + idTramo));

        if (tramo.getFechaHoraInicio() == null) {
            throw new RuntimeException("No se puede finalizar un tramo que no fue iniciado");
        }

        tramo.setFechaHoraFin(LocalDateTime.now());

        // Calcular costo real
        if (tramo.getCamion() != null && tramo.getDistanciaKm() != null) {
            Double costoReal = tramo.getCamion().calcularCostoTramo(tramo.getDistanciaKm());
            tramo.setCostoReal(costoReal);
        }

        Estado estadoFinalizado = estadoService.obtenerEstadoPorNombreYAmbito(
                "FINALIZADO", AmbitoEstado.TRAMO);
        tramo.setEstado(estadoFinalizado);

        // Liberar camión
        if (tramo.getCamion() != null) {
            camionService.marcarComoDisponible(tramo.getCamion().getPatente());
        }

        Tramo actualizado = tramoRepository.save(tramo);
        return toDTO(actualizado);
    }

    public List<TramoDTO> obtenerTramosEnProgreso() {
        Estado estadoIniciado = estadoService.obtenerEstadoPorNombreYAmbito(
                "INICIADO", AmbitoEstado.TRAMO);

        return tramoRepository.findByEstado(estadoIniciado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TramoDTO> obtenerTramosPendientesAsignacion() {
        Estado estadoEstimado = estadoService.obtenerEstadoPorNombreYAmbito(
                "ESTIMADO", AmbitoEstado.TRAMO);

        return tramoRepository.findByEstado(estadoEstimado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ======== MÉTODOS DE UTILIDAD ========

    public Tramo obtenerTramoEntity(Integer id) {
        return tramoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado con ID: " + id));
    }

    // ======== MAPPERS ========

    private TramoDTO toDTO(Tramo tramo) {
        if (tramo == null) return null;

        TramoDTO dto = new TramoDTO();
        dto.setIdTramo(tramo.getIdTramo());
        dto.setIdRuta(tramo.getRuta() != null ? tramo.getRuta().getIdRuta() : null);
        dto.setTipoTramo(tramo.getTipoTramo());
        dto.setDepositoInicio(depositoDTOFromEntity(tramo.getDepositoInicio()));
        dto.setDepositoFin(depositoDTOFromEntity(tramo.getDepositoFin()));
        dto.setInicioLat(tramo.getInicioLat());
        dto.setInicioLon(tramo.getInicioLon());
        dto.setFinLat(tramo.getFinLat());
        dto.setFinLon(tramo.getFinLon());
        dto.setEstado(estadoDTOFromEntity(tramo.getEstado()));
        dto.setDistanciaKm(tramo.getDistanciaKm());
        dto.setTiempoEstimado(tramo.getTiempoEstimado());
        dto.setCostoEstimado(tramo.getCostoEstimado());
        dto.setCostoReal(tramo.getCostoReal());
        dto.setFechaHoraInicio(tramo.getFechaHoraInicio());
        dto.setFechaHoraFin(tramo.getFechaHoraFin());
        dto.setCamion(camionDTOFromEntity(tramo.getCamion()));

        return dto;
    }

    private DepositoDTO depositoDTOFromEntity(Deposito deposito) {
        if (deposito == null) return null;
        return new DepositoDTO(
                deposito.getIdDeposito(),
                deposito.getNombre(),
                deposito.getDireccion(),
                deposito.getLatitud(),
                deposito.getLongitud(),
                deposito.getCostoEstadiaDiario()
        );
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

    private CamionDTO camionDTOFromEntity(Camion camion) {
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
}