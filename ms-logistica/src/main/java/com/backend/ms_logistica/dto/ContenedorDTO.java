package com.backend.ms_logistica.dto;

import com.backend.ms_logistica.model.UnidadPeso;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ContenedorDTO {

    private Integer idContenedor;

    private Integer idSolicitud;

    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser positivo")
    private Double peso;

    @NotNull(message = "La unidad de peso es obligatoria")
    private UnidadPeso unidadPeso;

    @NotNull(message = "El volumen es obligatorio")
    @Positive(message = "El volumen debe ser positivo")
    private Double volumen;

    private EstadoDTO estado;

    // Constructores
    public ContenedorDTO() {}

    public ContenedorDTO(Integer idContenedor, Integer idSolicitud, Double peso,
                         UnidadPeso unidadPeso, Double volumen, EstadoDTO estado) {
        this.idContenedor = idContenedor;
        this.idSolicitud = idSolicitud;
        this.peso = peso;
        this.unidadPeso = unidadPeso;
        this.volumen = volumen;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getIdContenedor() { return idContenedor; }
    public void setIdContenedor(Integer idContenedor) { this.idContenedor = idContenedor; }

    public Integer getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { this.idSolicitud = idSolicitud; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public UnidadPeso getUnidadPeso() { return unidadPeso; }
    public void setUnidadPeso(UnidadPeso unidadPeso) { this.unidadPeso = unidadPeso; }

    public Double getVolumen() { return volumen; }
    public void setVolumen(Double volumen) { this.volumen = volumen; }

    public EstadoDTO getEstado() { return estado; }
    public void setEstado(EstadoDTO estado) { this.estado = estado; }
}
