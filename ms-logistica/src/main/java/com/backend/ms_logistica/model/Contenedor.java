package com.backend.ms_logistica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CONTENEDOR")
public class Contenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contenedor")
    private Integer idContenedor;

    @OneToOne
    @JoinColumn(name = "id_solicitud", nullable = false, unique = true)
    private Solicitud solicitud;

    @Column(name = "peso", nullable = false)
    private Double peso;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_peso", nullable = false)
    private UnidadPeso unidadPeso;

    @Column(name = "volumen_m3", nullable = false)
    private Double volumen; // En metros cúbicos (m³)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = false)
    private Estado estado;

    public Contenedor() {}

    public Contenedor(Solicitud solicitud, Double peso, UnidadPeso unidadPeso,
                      Double volumen, Estado estado) {
        this.solicitud = solicitud;
        this.peso = peso;
        this.unidadPeso = unidadPeso;
        this.volumen = volumen;
        this.estado = estado;
    }

    // Getters y setters
    public Integer getIdContenedor() {
        return idContenedor;
    }

    public void setIdContenedor(Integer idContenedor) {
        this.idContenedor = idContenedor;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public UnidadPeso getUnidadPeso() {
        return unidadPeso;
    }

    public void setUnidadPeso(UnidadPeso unidadPeso) {
        this.unidadPeso = unidadPeso;
    }

    public Double getVolumen() {
        return volumen;
    }

    public void setVolumen(Double volumen) {
        this.volumen = volumen;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Convierte el peso a kilogramos para cálculos uniformes
     */
    public Double getPesoEnKilogramos() {
        return switch (unidadPeso) {
            case KILOGRAMO -> peso;
            case TONELADA -> peso * 1000;
            case LIBRA -> peso * 0.453592;
        };
    }
}