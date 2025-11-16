package com.backend.ms_logistica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CONTENEDOR")
public class Contenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contenedor")
    private Integer idContenedor;

    @ManyToOne
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;

    @Column(nullable = false, name = "tipo")
    private String tipo;  // Ej: CAJA, PALLET

    @Column(nullable = false, name = "volumen")
    private String volumen;  // KILOGRAMO, TONELADA, LIBRA

    @Column(name = "peso")
    private Double peso;

    public Contenedor() {}

    public Contenedor(Solicitud solicitud, String tipo, String volumen, Double peso) {
        this.solicitud = solicitud;
        this.tipo = tipo;
        this.volumen = volumen;
        this.peso = peso;
    }

    // Getters y setters
    public Integer getIdContenedor() { return idContenedor; }
    public void setIdContenedor(Integer idContenedor) { this.idContenedor = idContenedor; }

    public Solicitud getSolicitud() { return solicitud; }
    public void setSolicitud(Solicitud solicitud) { this.solicitud = solicitud; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getVolumen() { return volumen; }
    public void setVolumen(String volumen) { this.volumen = volumen; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
}
