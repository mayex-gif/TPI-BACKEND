// DTO para consultar estado de contenedor (Cliente)
package com.backend.ms_logistica.dto;

import java.time.LocalDateTime;

public class EstadoContenedorDTO {

    private Integer idSolicitud;
    private Integer idContenedor;
    private String estadoContenedor;
    private String estadoSolicitud;
    private String ubicacionActual;
    private LocalDateTime ultimaActualizacion;
    private Double porcentajeCompletado;

    public EstadoContenedorDTO() {}

    // Getters y Setters
    public Integer getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { this.idSolicitud = idSolicitud; }

    public Integer getIdContenedor() { return idContenedor; }
    public void setIdContenedor(Integer idContenedor) { this.idContenedor = idContenedor; }

    public String getEstadoContenedor() { return estadoContenedor; }
    public void setEstadoContenedor(String estadoContenedor) {
        this.estadoContenedor = estadoContenedor;
    }

    public String getEstadoSolicitud() { return estadoSolicitud; }
    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getUbicacionActual() { return ubicacionActual; }
    public void setUbicacionActual(String ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public LocalDateTime getUltimaActualizacion() { return ultimaActualizacion; }
    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public Double getPorcentajeCompletado() { return porcentajeCompletado; }
    public void setPorcentajeCompletado(Double porcentajeCompletado) {
        this.porcentajeCompletado = porcentajeCompletado;
    }
}