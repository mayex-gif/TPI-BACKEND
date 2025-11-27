package com.backend.ms_clientes.dto;

import jakarta.validation.constraints.*;

public class ClienteDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio.")
    @Size(min = 4, max = 30, message = "El username debe tener entre 4 y 30 caracteres.")
    private String username;

    private Integer idCliente;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "El DNI es obligatorio")
    @Min(value = 1000000, message = "El DNI debe tener al menos 7 dígitos")
    @Max(value = 9999999999L, message = "El DNI debe tener máximo 10 dígitos")
    private Long dni;

    private String telefono;

    @Email(message = "Email inválido")
    private String email;

    // --- NUEVO CAMPO: Contraseña para Keycloak ---
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    // ---------------------------------------------

    public ClienteDTO() {}

    public ClienteDTO(String nombre, String apellido, Long dni, String telefono, String email, String password, String username) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    // Getters y setters

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


    public Integer getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Long getDni() { return dni; }
    public void setDni(Long dni) { this.dni = dni; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}