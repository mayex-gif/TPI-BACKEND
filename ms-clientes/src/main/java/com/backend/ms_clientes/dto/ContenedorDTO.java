package com.backend.ms_clientes.dto;

public class ContenedorDTO {
    private Integer id;
    private Double peso;
    private String volumen;
    private String estado; // descripción del estado, no el ID
    private String cliente; // nombre del cliente
    private Integer id_estado;
    private Integer id_cliente;


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }
}
