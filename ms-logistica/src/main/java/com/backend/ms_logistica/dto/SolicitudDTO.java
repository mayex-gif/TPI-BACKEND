package com.backend.ms_logistica.dto;

import com.backend.ms_logistica.remoto.ClienteDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SolicitudDTO {

    private ClienteDTO cliente;

    private Integer idSolicitud;

    private Integer idCliente;

    private Integer idRuta;

    @NotNull(message = "El estado es obligatorio")
    private Integer idEstado;

    private String descripcion;

    private Double costoEstimado;

    private Double tiempoEstimado;

    private List<Integer> contenedoresIds;

    // Getters y setters


    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public Integer getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { this.idSolicitud = idSolicitud; }

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public Integer getIdRuta() { return idRuta; }
    public void setIdRuta(Integer idRuta) { this.idRuta = idRuta; }

    public Integer getIdEstado() { return idEstado; }
    public void setIdEstado(Integer idEstado) { this.idEstado = idEstado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getCostoEstimado() { return costoEstimado; }
    public void setCostoEstimado(Double costoEstimado) { this.costoEstimado = costoEstimado; }

    public Double getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(Double tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }

    public List<Integer> getContenedoresIds() { return contenedoresIds; }
    public void setContenedoresIds(List<Integer> contenedoresIds) { this.contenedoresIds = contenedoresIds; }
}
