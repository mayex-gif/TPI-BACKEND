package com.backend.ms_clientes.model;

import jakarta.persistence.*;

@Table
@Entity(name = "SOLICITUD")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SOLICITUD")
    private int id_solicitud;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "ID_CONTENEDOR")
    private Contenedor contenedor;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO")
    private Estado estado;

    @Column(name = "COSTO_ESTIMADO")
    private Double costo_estimado;

    @Column(name = "TIEMPO_ESTIMADO")
    private Double tiempo_estimado;

    @Column(name = "COSTO_FINAL")
    private Double costo_final;

    @Column(name = "TIEMPO_REAL")
    private Double tiempo_real;

    public Solicitud() {}
    public Solicitud(Cliente cliente, Contenedor contenedor, Estado estado, Double costo_estimado, Double tiempo_estimado, Double costo_final, Double tiempo_real) {
        this.cliente = cliente;
        this.contenedor = contenedor;
        this.estado = estado;
        this.costo_estimado = costo_estimado;
        this.tiempo_estimado = tiempo_estimado;
        this.costo_final = costo_final;
        this.tiempo_real = tiempo_real;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Contenedor getContenedor() {
        return contenedor;
    }

    public Estado getEstado() {
        return estado;
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

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setContenedor(Contenedor contenedor) {
        this.contenedor = contenedor;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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
