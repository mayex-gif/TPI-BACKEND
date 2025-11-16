package com.backend.ms_logistica.dto;

import jakarta.validation.constraints.NotBlank;

public class EstadoDTO {

    private Integer idEstado;

    @NotBlank(message = "El nombre del estado es obligatorio")
    private String nombre;

    private String descripcion;

    public EstadoDTO() {}

    public EstadoDTO(Integer idEstado, String nombre, String descripcion) {
        this.idEstado = idEstado;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y setters
    public Integer getIdEstado() { return idEstado; }
    public void setIdEstado(Integer idEstado) { this.idEstado = idEstado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
