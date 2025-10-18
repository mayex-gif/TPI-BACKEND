package com.backend.ms_clientes.model;

import jakarta.persistence.*;

@Table
@Entity(name = "CONTENEDOR")
public class Contenedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTENEDOR")
    private int id_contenedor;

    @Column(name = "PESO")
    private Double peso;

    @Enumerated(EnumType.STRING)
    @Column(name = "VOLUMEN")
    private Volumen volumen;

    public enum Volumen {KILOGRAMO, TONELADA, LIBRA}

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO")
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;

    public Contenedor() {}
    public Contenedor(Double peso, Volumen volumen, Estado estado, Cliente cliente) {
        this.peso = peso;
        this.volumen = volumen;
        this.estado = estado;
        this.cliente = cliente;
    }

    public int getId_contenedor() {
        return id_contenedor;
    }

    public Double getPeso() {
        return peso;
    }

    public Volumen getVolumen() {
        return volumen;
    }

    public Estado getEstado() {
        return estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public void setVolumen(Volumen volumen) {
        this.volumen = volumen;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
