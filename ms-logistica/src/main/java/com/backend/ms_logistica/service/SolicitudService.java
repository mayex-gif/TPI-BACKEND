package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.*;
import com.backend.ms_logistica.model.*;
import com.backend.ms_logistica.repository.SolicitudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final EstadoService estadoService;
    private final ContenedorService contenedorService;
    private final RutaService rutaService;

    public SolicitudService(SolicitudRepository solicitudRepository,
                            EstadoService estadoService,
                            ContenedorService contenedorService,
                            RutaService rutaService) {
        this.solicitudRepository = solicitudRepository;
        this.estadoService = estadoService;
        this.contenedorService = contenedorService;
        this.rutaService = rutaService;
    }

    // ============================================
    // OPERACIONES CRUD
    // ============================================

    public List<SolicitudDTO> obtenerTodas() {
        return solicitudRepository.findAll().stream()
                .map(this::toDTOSimple)
                .collect(Collectors.toList());
    }

    public SolicitudDTO obtenerPorId(Integer id) {
        Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + id));
        return toDTO(solicitud);
    }

    public List<SolicitudDTO> obtenerPorCliente(Integer idCliente) {
        return solicitudRepository.findByIdCliente(idCliente).stream()
                .map(this::toDTOSimple)
                .collect(Collectors.toList());
    }

    public List<SolicitudDTO> obtenerPorEstado(String nombreEstado) {
        Estado estado = estadoService.obtenerEstadoPorNombreYAmbito(
                nombreEstado, AmbitoEstado.SOLICITUD);

        return solicitudRepository.findByEstado(estado).stream()
                .map(this::toDTOSimple)
                .collect(Collectors.toList());
    }

    // ============================================
    // RF1: CREAR NUEVA SOLICITUD DE TRANSPORTE
    // ============================================

    /**
     * Crea una nueva solicitud de transporte con su contenedor
     * Estado inicial: BORRADOR
     */
    public SolicitudDTO crearSolicitud(SolicitudCreateDTO dto) {
        // Validar que el cliente exista (llamar a ms-clientes)
        // TODO: Implementar validación con RestTemplate

        // Crear solicitud
        Solicitud solicitud = fromCreateDTO(dto);

        // Asignar estado inicial: BORRADOR
        Estado estadoBorrador = estadoService.obtenerEstadoPorNombreYAmbito(
                "BORRADOR", AmbitoEstado.SOLICITUD);
        solicitud.setEstado(estadoBorrador);

        // Guardar solicitud
        Solicitud solicitudGuardada = solicitudRepository.save(solicitud);

        // Crear contenedor asociado
        Contenedor contenedor = contenedorService.crearContenedorParaSolicitud(
                solicitudGuardada,
                dto.getPeso(),
                dto.getUnidadPeso().name(),
                dto.getVolumen()
        );

        solicitudGuardada.setContenedor(contenedor);

        return toDTO(solicitudGuardada);
    }

    // ============================================
    // RF3: CONSULTAR RUTAS TENTATIVAS
    // ============================================

    /**
     * Genera y retorna rutas tentativas sin guardarlas
     */
    public List<RutaDTO> consultarRutasTentativas(Integer idSolicitud) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + idSolicitud));

        // Generar múltiples opciones de ruta
        // Opción 1: Ruta directa (sin depósitos)
        // Opción 2: Con 1 depósito intermedio
        // Opción 3: Con 2 depósitos intermedios

        // Por ahora retornamos solo la ruta directa como ejemplo
        // TODO: Implementar algoritmo de búsqueda de rutas óptimas

        return List.of(); // Placeholder
    }

    // ============================================
    // RF4: ASIGNAR RUTA A SOLICITUD
    // ============================================

    /**
     * Asigna una ruta definitiva a la solicitud
     * Cambia estado de BORRADOR a PROGRAMADA
     */
    public SolicitudDTO asignarRuta(Integer idSolicitud, List<Integer> idsDepositos) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + idSolicitud));

        if (!solicitud.getEstado().getNombre().equals("BORRADOR")) {
            throw new RuntimeException("Solo se pueden asignar rutas a solicitudes en estado BORRADOR");
        }

        // Generar ruta
        Ruta ruta;
        if (idsDepositos == null || idsDepositos.isEmpty()) {
            ruta = rutaService.generarRutaDirecta(solicitud);
        } else {
            ruta = rutaService.generarRutaConDepositos(solicitud, idsDepositos);
        }

        solicitud.setRuta(ruta);

        // Calcular costos y tiempos estimados
        Double costoTotal = ruta.getCostoEstimadoTotal();
        Double tiempoTotal = ruta.getTramos().stream()
                .map(Tramo::getTiempoEstimado)
                .reduce(0.0, Double::sum);

        solicitud.setCostoEstimado(costoTotal);
        solicitud.setTiempoEstimado(tiempoTotal);

        // Cambiar estado a PROGRAMADA
        Estado estadoProgramada = estadoService.obtenerEstadoPorNombreYAmbito(
                "PROGRAMADA", AmbitoEstado.SOLICITUD);
        solicitud.setEstado(estadoProgramada);

        Solicitud actualizada = solicitudRepository.save(solicitud);
        return toDTO(actualizada);
    }

    // ============================================
    // RF2: CONSULTAR ESTADO DEL TRANSPORTE
    // ============================================

    /**
     * Consulta el estado actual de un contenedor (para cliente)
     */
    public EstadoContenedorDTO consultarEstadoContenedor(Integer idSolicitud) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + idSolicitud));

        EstadoContenedorDTO dto = new EstadoContenedorDTO();
        dto.setIdSolicitud(solicitud.getIdSolicitud());
        dto.setEstadoSolicitud(solicitud.getEstado().getNombre());

        if (solicitud.getContenedor() != null) {
            dto.setIdContenedor(solicitud.getContenedor().getIdContenedor());
            dto.setEstadoContenedor(solicitud.getContenedor().getEstado().getNombre());
        }

        // Determinar ubicación actual
        String ubicacion = determinarUbicacionActual(solicitud);
        dto.setUbicacionActual(ubicacion);

        // Calcular porcentaje de completado
        Double porcentaje = calcularPorcentajeCompletado(solicitud);
        dto.setPorcentajeCompletado(porcentaje);

        dto.setUltimaActualizacion(LocalDateTime.now());

        return dto;
    }

    private String determinarUbicacionActual(Solicitud solicitud) {
        if (solicitud.getRuta() == null || solicitud.getRuta().getTramos().isEmpty()) {
            return "En origen";
        }

        // Buscar el último tramo iniciado o finalizado
        Tramo tramoActual = solicitud.getRuta().getTramos().stream()
                .filter(t -> t.getFechaHoraInicio() != null)
                .reduce((first, second) -> second)
                .orElse(null);

        if (tramoActual == null) {
            return "En origen";
        }

        if (tramoActual.getFechaHoraFin() != null) {
            // Tramo finalizado
            if (tramoActual.getDepositoFin() != null) {
                return "En depósito: " + tramoActual.getDepositoFin().getNombre();
            } else {
                return "En destino";
            }
        } else {
            // Tramo en progreso
            return "En tránsito";
        }
    }

    private Double calcularPorcentajeCompletado(Solicitud solicitud) {
        if (solicitud.getRuta() == null || solicitud.getRuta().getTramos().isEmpty()) {
            return 0.0;
        }

        int tramosFinalizados = (int) solicitud.getRuta().getTramos().stream()
                .filter(t -> t.getFechaHoraFin() != null)
                .count();

        int tramosTotal = solicitud.getRuta().getTramos().size();

        return (tramosFinalizados * 100.0) / tramosTotal;
    }

    // ============================================
    // RF5: CONSULTAR CONTENEDORES PENDIENTES
    // ============================================

    /**
     * Obtiene todas las solicitudes pendientes con filtros
     */
    public List<SolicitudDTO> obtenerSolicitudesPendientes(String estadoFiltro) {
        if (estadoFiltro != null && !estadoFiltro.isEmpty()) {
            return obtenerPorEstado(estadoFiltro);
        }

        // Retornar solicitudes en estados PROGRAMADA o EN_TRANSITO
        List<String> estadosPendientes = List.of("PROGRAMADA", "EN_TRANSITO");

        return solicitudRepository.findAll().stream()
                .filter(s -> estadosPendientes.contains(s.getEstado().getNombre()))
                .map(this::toDTOSimple)
                .collect(Collectors.toList());
    }

    // ============================================
    // RF10: FINALIZAR SOLICITUD Y CALCULAR COSTOS
    // ============================================

    /**
     * Finaliza una solicitud calculando costos y tiempos reales
     */
    public SolicitudDTO finalizarSolicitud(Integer idSolicitud) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + idSolicitud));

        if (solicitud.getRuta() == null) {
            throw new RuntimeException("La solicitud no tiene ruta asignada");
        }

        // Verificar que todos los tramos estén finalizados
        boolean todosFinalizados = solicitud.getRuta().getTramos().stream()
                .allMatch(t -> t.getFechaHoraFin() != null);

        if (!todosFinalizados) {
            throw new RuntimeException("No se puede finalizar la solicitud: hay tramos pendientes");
        }

        // Calcular costo real total
        Double costoRealTotal = solicitud.getRuta().getTramos().stream()
                .map(Tramo::getCostoReal)
                .reduce(0.0, Double::sum);

        solicitud.setCostoFinal(costoRealTotal);

        // Calcular tiempo real total (en horas)
        LocalDateTime inicio = solicitud.getRuta().getTramos().get(0).getFechaHoraInicio();
        LocalDateTime fin = solicitud.getRuta().getTramos()
                .get(solicitud.getRuta().getTramos().size() - 1).getFechaHoraFin();

        if (inicio != null && fin != null) {
            Duration duracion = Duration.between(inicio, fin);
            Double horasReales = duracion.toMinutes() / 60.0;
            solicitud.setTiempoReal(horasReales);
        }

        // Cambiar estado a ENTREGADA
        Estado estadoEntregada = estadoService.obtenerEstadoPorNombreYAmbito(
                "ENTREGADA", AmbitoEstado.SOLICITUD);
        solicitud.setEstado(estadoEntregada);

        // Cambiar estado del contenedor a EN_DESTINO
        if (solicitud.getContenedor() != null) {
            contenedorService.cambiarEstado(
                    solicitud.getContenedor().getIdContenedor(),
                    "EN_DESTINO"
            );
        }

        Solicitud actualizada = solicitudRepository.save(solicitud);
        return toDTO(actualizada);
    }

    // ============================================
    // MÉTODOS DE UTILIDAD
    // ============================================

    public Solicitud obtenerSolicitudEntity(Integer id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + id));
    }

    // ============================================
    // MAPPERS
    // ============================================

    private SolicitudDTO toDTO(Solicitud solicitud) {
        if (solicitud == null) return null;

        SolicitudDTO dto = new SolicitudDTO();
        dto.setIdSolicitud(solicitud.getIdSolicitud());
        dto.setIdCliente(solicitud.getIdCliente());
        dto.setEstado(estadoDTOFromEntity(solicitud.getEstado()));
        dto.setFechaSolicitud(solicitud.getFechaSolicitud());
        dto.setDescripcion(solicitud.getDescripcion());
        dto.setOrigenDireccion(solicitud.getOrigenDireccion());
        dto.setOrigenLat(solicitud.getOrigenLat());
        dto.setOrigenLon(solicitud.getOrigenLon());
        dto.setDestinoDireccion(solicitud.getDestinoDireccion());
        dto.setDestinoLat(solicitud.getDestinoLat());
        dto.setDestinoLon(solicitud.getDestinoLon());
        dto.setCostoEstimado(solicitud.getCostoEstimado());
        dto.setTiempoEstimado(solicitud.getTiempoEstimado());
        dto.setCostoFinal(solicitud.getCostoFinal());
        dto.setTiempoReal(solicitud.getTiempoReal());
        dto.setContenedor(contenedorDTOFromEntity(solicitud.getContenedor()));
        dto.setRuta(rutaDTOFromEntity(solicitud.getRuta()));

        return dto;
    }

    private SolicitudDTO toDTOSimple(Solicitud solicitud) {
        if (solicitud == null) return null;

        SolicitudDTO dto = new SolicitudDTO();
        dto.setIdSolicitud(solicitud.getIdSolicitud());
        dto.setIdCliente(solicitud.getIdCliente());
        dto.setEstado(estadoDTOFromEntity(solicitud.getEstado()));
        dto.setFechaSolicitud(solicitud.getFechaSolicitud());
        dto.setDescripcion(solicitud.getDescripcion());
        dto.setOrigenDireccion(solicitud.getOrigenDireccion());
        dto.setDestinoDireccion(solicitud.getDestinoDireccion());
        dto.setCostoEstimado(solicitud.getCostoEstimado());
        dto.setTiempoEstimado(solicitud.getTiempoEstimado());
        dto.setCostoFinal(solicitud.getCostoFinal());
        dto.setTiempoReal(solicitud.getTiempoReal());

        return dto;
    }

    private Solicitud fromCreateDTO(SolicitudCreateDTO dto) {
        if (dto == null) return null;

        Solicitud solicitud = new Solicitud();
        solicitud.setIdCliente(dto.getIdCliente());
        solicitud.setDescripcion(dto.getDescripcion());
        solicitud.setOrigenDireccion(dto.getOrigenDireccion());
        solicitud.setOrigenLat(dto.getOrigenLat());
        solicitud.setOrigenLon(dto.getOrigenLon());
        solicitud.setDestinoDireccion(dto.getDestinoDireccion());
        solicitud.setDestinoLat(dto.getDestinoLat());
        solicitud.setDestinoLon(dto.getDestinoLon());

        return solicitud;
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

    private ContenedorDTO contenedorDTOFromEntity(Contenedor contenedor) {
        if (contenedor == null) return null;

        ContenedorDTO dto = new ContenedorDTO();
        dto.setIdContenedor(contenedor.getIdContenedor());
        dto.setPeso(contenedor.getPeso());
        dto.setUnidadPeso(contenedor.getUnidadPeso());
        dto.setVolumen(contenedor.getVolumen());
        dto.setEstado(estadoDTOFromEntity(contenedor.getEstado()));

        return dto;
    }

    private RutaDTO rutaDTOFromEntity(Ruta ruta) {
        if (ruta == null) return null;

        RutaDTO dto = new RutaDTO();
        dto.setIdRuta(ruta.getIdRuta());
        dto.setCantidadTramos(ruta.getCantidadTramos());
        dto.setCantidadDepositos(ruta.getCantidadDepositos());
        dto.setDistanciaTotal(ruta.getDistanciaTotal());
        dto.setCostoEstimadoTotal(ruta.getCostoEstimadoTotal());

        return dto;
    }
}