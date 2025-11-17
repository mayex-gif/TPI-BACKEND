package com.backend.ms_logistica.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RUTA")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private Integer idRuta;

    @Column(nullable = false, name = "origen")
    private String origen;

    @Column(nullable = false, name = "destino")
    private String destino;

    @Column(name = "distancia_km", nullable = false)
    private Double distanciaKm;

    @Column(name = "costo_base", nullable = false)
    private Double costoBase;

    @OneToMany(mappedBy = "ruta")
    @JsonManagedReference
    private List<Tramo> tramos = new ArrayList<>();

    public Ruta() {}

    public Ruta(String origen, String destino, Double distanciaKm, Double costoBase) {
        this.origen = origen;
        this.destino = destino;
        this.distanciaKm = distanciaKm;
        this.costoBase = costoBase;
    }

    // Getters y setters
    public Integer getIdRuta() { return idRuta; }
    public void setIdRuta(Integer idRuta) { this.idRuta = idRuta; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public Double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(Double distanciaKm) { this.distanciaKm = distanciaKm; }

    public Double getCostoBase() { return costoBase; }
    public void setCostoBase(Double costoBase) { this.costoBase = costoBase; }

    public List<Tramo> getTramos() { return tramos; }
    public void setTramos(List<Tramo> tramos) { this.tramos = tramos; }

    // Métodos auxiliares para agregar y remover tramos
    public void agregarTramo(Tramo tramo) {
        tramos.add(tramo);
        tramo.setRuta(this);
    }

    public void removerTramo(Tramo tramo) {
        tramos.remove(tramo);
        tramo.setRuta(null);
    }
}
