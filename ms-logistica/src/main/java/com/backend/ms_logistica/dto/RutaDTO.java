package com.backend.ms_logistica.dto;

public class RutaDTO {
    private Integer id_ruta;
    private int cantidad_tramos;
    private int cantidad_depositos;
    private Double distance_total;
    private Integer id_solicitud;

    public int getCantidad_tramos() {
        return cantidad_tramos;
    }

    public void setCantidad_tramos(int cantidad_tramos) {
        this.cantidad_tramos = cantidad_tramos;
    }

    public int getCantidad_depositos() {
        return cantidad_depositos;
    }

    public void setCantidad_depositos(int cantidad_depositos) {
        this.cantidad_depositos = cantidad_depositos;
    }

    public Double getDistance_total() {
        return distance_total;
    }

    public void setDistance_total(Double distance_total) {
        this.distance_total = distance_total;
    }

    public Integer getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(Integer id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public Integer getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(Integer id_ruta) {
        this.id_ruta = id_ruta;
    }
}
