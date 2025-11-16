package com.backend.ms_logistica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ContenedorDTO {

    private Integer idContenedor;

    @NotNull(message = "La solicitud es obligatoria")
    private Integer idSolicitud;  // solo se pasa el ID


    private String tipo;


    private String volumen;

    private Double peso;

    public ContenedorDTO() {}

    public ContenedorDTO(Integer idContenedor, Integer idSolicitud, String tipo, String volumen, Double peso) {
        this.idContenedor = idContenedor;
        this.idSolicitud = idSolicitud;
        this.tipo = tipo;
        this.volumen = volumen;
        this.peso = peso;
    }

    // Getters y setters
    public Integer getIdContenedor() { return idContenedor; }
    public void setIdContenedor(Integer idContenedor) { this.idContenedor = idContenedor; }

    public Integer getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { this.idSolicitud = idSolicitud; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getVolumen() { return volumen; }
    public void setVolumen(String volumen) { this.volumen = volumen; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
}
