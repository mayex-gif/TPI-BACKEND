package com.backend.ms_logistica.dto;

import com.backend.ms_logistica.model.AmbitoEstado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EstadoDTO {

    private Integer idEstado;

    @NotNull(message = "El ámbito es obligatorio")
    private AmbitoEstado ambito;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    // Constructores
    public EstadoDTO() {}

    public EstadoDTO(Integer idEstado, AmbitoEstado ambito, String nombre, String descripcion) {
        this.idEstado = idEstado;
        this.ambito = ambito;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Integer getIdEstado() { return idEstado; }
    public void setIdEstado(Integer idEstado) { this.idEstado = idEstado; }

    public AmbitoEstado getAmbito() { return ambito; }
    public void setAmbito(AmbitoEstado ambito) { this.ambito = ambito; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}