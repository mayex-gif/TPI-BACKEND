package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.TramoDTO;
import com.backend.ms_logistica.model.Camion;
import com.backend.ms_logistica.model.Estado;
import com.backend.ms_logistica.model.Tramo;
import com.backend.ms_logistica.model.TipoTramo;
import com.backend.ms_logistica.repository.CamionRepository;
import com.backend.ms_logistica.repository.DepositoRepository;
import com.backend.ms_logistica.repository.EstadoRepository;
import com.backend.ms_logistica.repository.RutaRepository;
import com.backend.ms_logistica.repository.TramoRepository;
import com.backend.ms_logistica.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class TramoService {

    @Autowired
    private TramoRepository tramoRepo;

    @Autowired
    private RutaRepository rutaRepo;

    @Autowired
    private EstadoRepository estadoRepo;

    @Autowired
    private CamionRepository camionRepo;

    @Autowired
    private DepositoRepository depositoRepo;

    @Autowired
    private UbicacionRepository ubicacionRepo;

    private final RestTemplate restTemplate = new RestTemplate();

    // =========================
    // CRUD
    // =========================
    public Tramo crear(Tramo tramo) {
        Double origenLat = getLatitud(tramo.getIdOrigen(), true, tramo.getTipoTramo());
        Double origenLng = getLongitud(tramo.getIdOrigen(), true, tramo.getTipoTramo());
        Double destinoLat = getLatitud(tramo.getIdDestino(), false, tramo.getTipoTramo());
        Double destinoLng = getLongitud(tramo.getIdDestino(), false, tramo.getTipoTramo());

        calcularDistanciaYTiempo(tramo, origenLat, origenLng, destinoLat, destinoLng);

        return tramoRepo.save(tramo);
    }

    public Tramo buscar(Integer id) {
        return tramoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado: " + id));
    }

    public List<Tramo> listar() {
        return tramoRepo.findAll();
    }

    public Tramo actualizar(Integer id, Tramo nuevo) {
        Tramo t = buscar(id);
        t.setRuta(nuevo.getRuta());
        t.setTipoTramo(nuevo.getTipoTramo());
        t.setIdOrigen(nuevo.getIdOrigen());
        t.setIdDestino(nuevo.getIdDestino());
        t.setEstado(nuevo.getEstado());
        t.setCostoAprox(nuevo.getCostoAprox());
        t.setCostoReal(nuevo.getCostoReal());
        t.setTiempoReal(nuevo.getTiempoReal());
        t.setCamion(nuevo.getCamion());
        t.setFechaHoraInicio(nuevo.getFechaHoraInicio());
        t.setFechaHoraFin(nuevo.getFechaHoraFin());
        return tramoRepo.save(t);
    }

    public void eliminar(Integer id) {
        tramoRepo.deleteById(id);
    }

    // =========================
    // Mapeo DTO <-> Entity
    // =========================
    public Tramo toEntity(TramoDTO dto) {
        Tramo t = new Tramo();
        t.setIdTramo(dto.getIdTramo());
        t.setRuta(rutaRepo.findById(dto.getIdRuta()).orElseThrow());
        t.setTipoTramo(dto.getTipoTramo());
        t.setIdOrigen(dto.getIdOrigen());
        t.setIdDestino(dto.getIdDestino());
        t.setEstado(estadoRepo.findById(dto.getEstadoId()).orElseThrow());
        t.setCostoAprox(dto.getCostoAprox());
        t.setCostoReal(dto.getCostoReal());
        t.setTiempoEstimado(dto.getTiempoEstimado());
        t.setTiempoReal(dto.getTiempoReal());
        t.setDistanciaKm(dto.getDistanciaKm());
        if (dto.getDominioCamion() != null) {
            t.setCamion(camionRepo.findById(dto.getDominioCamion()).orElseThrow());
        }
        return t;
    }

    public TramoDTO toDTO(Tramo t) {
        TramoDTO dto = new TramoDTO();
        dto.setIdTramo(t.getIdTramo());
        dto.setIdRuta(t.getRuta().getIdRuta());
        dto.setTipoTramo(t.getTipoTramo());
        dto.setIdOrigen(t.getIdOrigen());
        dto.setIdDestino(t.getIdDestino());
        dto.setEstadoId(t.getEstado().getIdEstado());
        dto.setCostoAprox(t.getCostoAprox());
        dto.setCostoReal(t.getCostoReal());
        dto.setTiempoEstimado(t.getTiempoEstimado());
        dto.setTiempoReal(t.getTiempoReal());
        dto.setDistanciaKm(t.getDistanciaKm());
        dto.setDominioCamion(t.getCamion() != null ? t.getCamion().getPatente() : null);
        return dto;
    }

    // =========================
    // OSRM
    // =========================
    public void calcularDistanciaYTiempo(Tramo tramo, Double origenLat, Double origenLng, Double destinoLat, Double destinoLng) {
        String url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:5000/route/v1/driving/{origenLng},{origenLat};{destinoLng},{destinoLat}")
                .queryParam("overview", "false")
                .queryParam("annotations", "duration,distance")
                .buildAndExpand(origenLng, origenLat, destinoLng, destinoLat)
                .toUriString();

        OSRMResponse response = restTemplate.getForObject(url, OSRMResponse.class);
        if (response != null && response.getRoutes() != null && !response.getRoutes().isEmpty()) {
            Double distanciaMetros = response.getRoutes().get(0).getDistance();
            Double duracionSegundos = response.getRoutes().get(0).getDuration();

            tramo.setDistanciaKm(distanciaMetros / 1000.0); // km
            tramo.setTiempoEstimado(duracionSegundos / 60.0); // minutos
        }
    }

    private Double getLatitud(Integer id, boolean esOrigen, TipoTramo tipoTramo) {
        switch (tipoTramo) {
            case ORIGEN_DEPOSITO:
            case DEPOSITO_DEPOSITO:
            case DEPOSITO_DESTINO:
                return depositoRepo.findById(id).orElseThrow().getLatitud();
            case ORIGEN_DESTINO:
                return ubicacionRepo.findById(id).orElseThrow().getLatitud();
            default: throw new IllegalArgumentException("TipoTramo desconocido: " + tipoTramo);
        }
    }

    private Double getLongitud(Integer id, boolean esOrigen, TipoTramo tipoTramo) {
        switch (tipoTramo) {
            case ORIGEN_DEPOSITO:
            case DEPOSITO_DEPOSITO:
            case DEPOSITO_DESTINO:
                return depositoRepo.findById(id).orElseThrow().getLongitud();
            case ORIGEN_DESTINO:
                return ubicacionRepo.findById(id).orElseThrow().getLongitud();
            default: throw new IllegalArgumentException("TipoTramo desconocido: " + tipoTramo);
        }
    }

    // =========================
    // Clase interna para OSRM
    // =========================
    public static class OSRMResponse {
        private List<Route> routes;
        public List<Route> getRoutes() { return routes; }
        public void setRoutes(List<Route> routes) { this.routes = routes; }

        public static class Route {
            private Double distance;
            private Double duration;
            public Double getDistance() { return distance; }
            public void setDistance(Double distance) { this.distance = distance; }
            public Double getDuration() { return duration; }
        }
    }
}
