package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.*;
import com.backend.ms_logistica.model.*;
import com.backend.ms_logistica.repository.RutaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class RutaService {

    private final OsrmService osrmService;

    private final RutaRepository rutaRepository;
    private final TramoService tramoService;
    private final EstadoService estadoService;
    private final DepositoService depositoService;
    private final CamionService camionService;

    private final RestTemplate restTemplate = new RestTemplate();

    public RutaService(RutaRepository rutaRepository, TramoService tramoService,
                       EstadoService estadoService, DepositoService depositoService,
                       OsrmService osrmService, CamionService camionService) {
        this.rutaRepository = rutaRepository;
        this.tramoService = tramoService;
        this.estadoService = estadoService;
        this.depositoService = depositoService;
        this.osrmService = osrmService;
        this.camionService = camionService;
    }

    // ======== OPERACIONES CRUD ========

    public RutaDTO obtenerPorId(Integer id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con ID: " + id));
        return toDTO(ruta);
    }

    public List<RutaDTO> obtenerTodas() {
        return rutaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RutaDTO obtenerPorSolicitud(Integer idSolicitud) {
        Ruta ruta = rutaRepository.findBySolicitudIdSolicitud(idSolicitud)
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró ruta para la solicitud: " + idSolicitud));
        return toDTO(ruta);
    }

    // ======== OPERACIONES DE NEGOCIO ========

    /**
     * Genera una ruta directa (sin depósitos) entre origen y destino
     */
    public Ruta generarRutaDirecta(Solicitud solicitud) {
        Ruta ruta = new Ruta(solicitud);
        ruta = rutaRepository.save(ruta);

        // Crear tramo directo ORIGEN_DESTINO
        Tramo tramo = new Tramo();
        tramo.setRuta(ruta);
        tramo.setTipoTramo(TipoTramo.ORIGEN_DESTINO);
        tramo.setInicioLat(solicitud.getOrigenLat());
        tramo.setInicioLon(solicitud.getOrigenLon());
        tramo.setFinLat(solicitud.getDestinoLat());
        tramo.setFinLon(solicitud.getDestinoLon());

        // Estado inicial: ESTIMADO
        Estado estadoEstimado = estadoService.obtenerEstadoPorNombreYAmbito(
                "ESTIMADO", AmbitoEstado.TRAMO);
        tramo.setEstado(estadoEstimado);

        // 🚨 USO DE OSRM
        Map<String, Double> metricas = osrmService.calcularMetricas(
                solicitud.getOrigenLat(), solicitud.getOrigenLon(),
                solicitud.getDestinoLat(), solicitud.getDestinoLon()
        );

        tramo.setDistanciaKm(metricas.get("distanceKm"));
        tramo.setTiempoEstimado(metricas.get("durationHours")); // Tiempo en horas

        ruta.agregarTramo(tramo);
        return rutaRepository.save(ruta);
    }

    /**
     * Genera una ruta con depósitos intermedios
     */
    public Ruta generarRutaConDepositos(Solicitud solicitud, List<Integer> idsDepositos) {
        if (idsDepositos == null || idsDepositos.isEmpty()) {
            return generarRutaDirecta(solicitud);
        }

        Ruta ruta = new Ruta(solicitud);
        ruta = rutaRepository.save(ruta);

        Estado estadoEstimado = estadoService.obtenerEstadoPorNombreYAmbito(
                "ESTIMADO", AmbitoEstado.TRAMO);

        // Primer tramo: ORIGEN → Primer Depósito
        Deposito primerDeposito = depositoService.obtenerDepositoEntity(idsDepositos.get(0));
        Tramo tramoInicial = crearTramo(
                ruta,
                TipoTramo.ORIGEN_DEPOSITO,
                solicitud.getOrigenLat(), solicitud.getOrigenLon(),
                null,
                primerDeposito,
                estadoEstimado
        );
        ruta.agregarTramo(tramoInicial);

        // Tramos intermedios: Depósito → Depósito
        for (int i = 0; i < idsDepositos.size() - 1; i++) {
            Deposito depositoActual = depositoService.obtenerDepositoEntity(idsDepositos.get(i));
            Deposito depositoSiguiente = depositoService.obtenerDepositoEntity(idsDepositos.get(i + 1));

            Tramo tramoIntermedio = crearTramo(
                    ruta,
                    TipoTramo.DEPOSITO_DEPOSITO,
                    null, null,
                    depositoActual,
                    depositoSiguiente,
                    estadoEstimado
            );
            ruta.agregarTramo(tramoIntermedio);
        }

        // Último tramo: Último Depósito → DESTINO
        Deposito ultimoDeposito = depositoService.obtenerDepositoEntity(
                idsDepositos.get(idsDepositos.size() - 1));
        Tramo tramoFinal = crearTramo(
                ruta,
                TipoTramo.DEPOSITO_DESTINO,
                null, null,
                ultimoDeposito,
                null,
                estadoEstimado
        );
        tramoFinal.setFinLat(solicitud.getDestinoLat());
        tramoFinal.setFinLon(solicitud.getDestinoLon());
        ruta.agregarTramo(tramoFinal);

        return rutaRepository.save(ruta);
    }

    private Tramo crearTramo(Ruta ruta, TipoTramo tipo,
                             Double inicioLat, Double inicioLon,
                             Deposito depositoInicio, Deposito depositoFin,
                             Estado estado) {
        Tramo tramo = new Tramo();
        tramo.setRuta(ruta);
        tramo.setTipoTramo(tipo);
        tramo.setInicioLat(inicioLat);
        tramo.setInicioLon(inicioLon);
        tramo.setDepositoInicio(depositoInicio);
        tramo.setDepositoFin(depositoFin);
        tramo.setEstado(estado);

        // Calcular coordenadas efectivas
        Double latInicio = tramo.getLatitudInicio();
        Double lonInicio = tramo.getLongitudInicio();
        Double latFin = tramo.getLatitudFin();
        Double lonFin = tramo.getLongitudFin();

        if (latInicio != null && lonInicio != null && latFin != null && lonFin != null) {
            // 🚨 USO DE OSRM
            Map<String, Double> metricas = osrmService.calcularMetricas(
                    latInicio, lonInicio, latFin, lonFin
            );

            tramo.setDistanciaKm(metricas.get("distanceKm"));
            tramo.setTiempoEstimado(metricas.get("durationHours"));
            // TODO: Lógica de costo aquí
        }

        return tramo;
    }

    // ======== MAPPERS ========

    private RutaDTO toDTO(Ruta ruta) {
        if (ruta == null) return null;

        RutaDTO dto = new RutaDTO();
        dto.setIdRuta(ruta.getIdRuta());
        dto.setIdSolicitud(ruta.getSolicitud() != null ?
                ruta.getSolicitud().getIdSolicitud() : null);

        if (ruta.getTramos() != null) {
            dto.setTramos(ruta.getTramos().stream()
                    .map(this::tramoDTOSimple)
                    .collect(Collectors.toList()));
        }

        dto.setCantidadTramos(ruta.getCantidadTramos());
        dto.setCantidadDepositos(ruta.getCantidadDepositos());
        dto.setDistanciaTotal(ruta.getDistanciaTotal());
        dto.setCostoEstimadoTotal(ruta.getCostoEstimadoTotal());

        return dto;
    }

    private TramoDTO tramoDTOSimple(Tramo tramo) {
        if (tramo == null) return null;

        TramoDTO dto = new TramoDTO();
        dto.setIdTramo(tramo.getIdTramo());
        dto.setTipoTramo(tramo.getTipoTramo());
        dto.setDistanciaKm(tramo.getDistanciaKm());
        dto.setTiempoEstimado(tramo.getTiempoEstimado());
        dto.setCostoEstimado(tramo.getCostoEstimado());
        dto.setCostoReal(tramo.getCostoReal());
        dto.setEstado(estadoDTOFromEntity(tramo.getEstado()));

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