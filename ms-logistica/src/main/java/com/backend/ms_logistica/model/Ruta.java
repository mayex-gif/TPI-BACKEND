package com.backend.ms_logistica.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RUTA")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private Integer idRuta;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_solicitud", nullable = false, unique = true)
    private Solicitud solicitud;

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Tramo> tramos = new ArrayList<>();

    public Ruta() {}

    public Ruta(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    // ======================================
    //  ATRIBUTOS CALCULADOS (NO BD)
    // ======================================

    @Transient
    @JsonProperty("cantidad_tramos")
    public int getCantidadTramos() {
        return tramos != null ? tramos.size() : 0;
    }

    @Transient
    @JsonProperty("cantidad_depositos")
    public long getCantidadDepositos() {
        if (tramos == null || tramos.isEmpty()) {
            return 0;
        }

        return tramos.stream()
                .filter(t -> t.getTipoTramo() != null &&
                        (t.getTipoTramo() == TipoTramo.ORIGEN_DEPOSITO ||
                                t.getTipoTramo() == TipoTramo.DEPOSITO_DEPOSITO ||
                                t.getTipoTramo() == TipoTramo.DEPOSITO_DESTINO))
                .count();
    }

    @Transient
    @JsonProperty("distancia_total")
    public Double getDistanciaTotal() {
        if (tramos == null || tramos.isEmpty()) {
            return 0.0;
        }

        return tramos.stream()
                .map(Tramo::getDistanciaKm)
                .filter(d -> d != null)
                .reduce(0.0, Double::sum);
    }

    @Transient
    @JsonProperty("costo_estimado_total")
    public Double getCostoEstimadoTotal() {
        if (tramos == null || tramos.isEmpty()) {
            return 0.0;
        }

        return tramos.stream()
                .map(Tramo::getCostoEstimado)
                .filter(c -> c != null)
                .reduce(0.0, Double::sum);
    }

    // ======================================
    // Getters y Setters
    // ======================================

    public Integer getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public List<Tramo> getTramos() {
        return tramos;
    }

    public void setTramos(List<Tramo> tramos) {
        this.tramos = tramos;
    }

    // ======================================
    // Métodos auxiliares
    // ======================================

    public void agregarTramo(Tramo tramo) {
        tramos.add(tramo);
        tramo.setRuta(this);
    }

    public void removerTramo(Tramo tramo) {
        tramos.remove(tramo);
        tramo.setRuta(null);
    }

    public void limpiarTramos() {
        if (tramos != null) {
            tramos.clear();
        }
    }
}