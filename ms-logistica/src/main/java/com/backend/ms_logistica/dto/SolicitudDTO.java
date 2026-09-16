package com.backend.ms_logistica.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class SolicitudDTO {

    private Integer idSolicitud;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer idCliente;

    private EstadoDTO estado;

    private LocalDateTime fechaSolicitud;

    private String descripcion;

    // Origen
    private String origenDireccion;

    @NotNull(message = "La latitud de origen es obligatoria")
    private Double origenLat;

    @NotNull(message = "La longitud de origen es obligatoria")
    private Double origenLon;

    // Destino
    private String destinoDireccion;

    @NotNull(message = "La latitud de destino es obligatoria")
    private Double destinoLat;

    @NotNull(message = "La longitud de destino es obligatoria")
    private Double destinoLon;

    // Costos y tiempos
    private Double costoEstimado;
    private Double tiempoEstimado;
    private Double costoFinal;
    private Double tiempoReal;

    // Relaciones
    private ContenedorDTO contenedor;
    private RutaDTO ruta;

    // Constructores
    public SolicitudDTO() {}

    // Getters y Setters
    public Integer getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { this.idSolicitud = idSolicitud; }

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public EstadoDTO getEstado() { return estado; }
    public void setEstado(EstadoDTO estado) { this.estado = estado; }

    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getOrigenDireccion() { return origenDireccion; }
    public void setOrigenDireccion(String origenDireccion) {
        this.origenDireccion = origenDireccion;
    }

    public Double getOrigenLat() { return origenLat; }
    public void setOrigenLat(Double origenLat) { this.origenLat = origenLat; }

    public Double getOrigenLon() { return origenLon; }
    public void setOrigenLon(Double origenLon) { this.origenLon = origenLon; }

    public String getDestinoDireccion() { return destinoDireccion; }
    public void setDestinoDireccion(String destinoDireccion) {
        this.destinoDireccion = destinoDireccion;
    }

    public Double getDestinoLat() { return destinoLat; }
    public void setDestinoLat(Double destinoLat) { this.destinoLat = destinoLat; }

    public Double getDestinoLon() { return destinoLon; }
    public void setDestinoLon(Double destinoLon) { this.destinoLon = destinoLon; }

    public Double getCostoEstimado() { return costoEstimado; }
    public void setCostoEstimado(Double costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public Double getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(Double tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public Double getCostoFinal() { return costoFinal; }
    public void setCostoFinal(Double costoFinal) { this.costoFinal = costoFinal; }

    public Double getTiempoReal() { return tiempoReal; }
    public void setTiempoReal(Double tiempoReal) { this.tiempoReal = tiempoReal; }

    public ContenedorDTO getContenedor() { return contenedor; }
    public void setContenedor(ContenedorDTO contenedor) { this.contenedor = contenedor; }

    public RutaDTO getRuta() { return ruta; }
    public void setRuta(RutaDTO ruta) { this.ruta = ruta; }
}
