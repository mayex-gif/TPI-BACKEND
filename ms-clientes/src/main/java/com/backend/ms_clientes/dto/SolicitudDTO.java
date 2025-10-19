package com.backend.ms_clientes.dto;

public class SolicitudDTO {

    private Integer id_cliente;
    private Integer id_contenedor;
    private Integer id_estado;
    private Double costo_estimado;
    private Double tiempo_estimado;
    private Double costo_final;
    private Double tiempo_real;

    public Integer getId_cliente() {
        return id_cliente;
    }

    public Integer getId_contenedor() {
        return id_contenedor;
    }

    public Integer getId_estado() {
        return id_estado;
    }

    public Double getCosto_estimado() {
        return costo_estimado;
    }

    public Double getTiempo_estimado() {
        return tiempo_estimado;
    }

    public Double getCosto_final() {
        return costo_final;
    }

    public Double getTiempo_real() {
        return tiempo_real;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setId_contenedor(Integer id_contenedor) {
        this.id_contenedor = id_contenedor;
    }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }

    public void setCosto_estimado(Double costo_estimado) {
        this.costo_estimado = costo_estimado;
    }

    public void setTiempo_estimado(Double tiempo_estimado) {
        this.tiempo_estimado = tiempo_estimado;
    }

    public void setCosto_final(Double costo_final) {
        this.costo_final = costo_final;
    }

    public void setTiempo_real(Double tiempo_real) {
        this.tiempo_real = tiempo_real;
    }
}
