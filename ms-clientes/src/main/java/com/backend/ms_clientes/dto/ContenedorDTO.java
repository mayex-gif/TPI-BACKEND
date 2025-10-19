package com.backend.ms_clientes.dto;

public class ContenedorDTO {
    private Double peso;
    private String volumen;
    private Integer id_estado;
    private Integer id_cliente;

    public Double getPeso() {
        return peso;
    }

    public String getVolumen() {
        return volumen;
    }

    public Integer getId_estado() {
        return id_estado;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }
}
