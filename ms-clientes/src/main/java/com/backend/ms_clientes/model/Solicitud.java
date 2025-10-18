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
    private Solicitud(Cliente cliente, Contenedor contenedor, Estado estado, Double costo_estimado, Double tiempo_estimado, Double costo_final, Double tiempo_real) {
        this.cliente = cliente;
        this.contenedor = contenedor;
        this.estado = estado;
        this.costo_estimado = costo_estimado;
        this.tiempo_estimado = tiempo_estimado;
        this.costo_final = costo_final;
        this.tiempo_real = tiempo_real;
    }

}
