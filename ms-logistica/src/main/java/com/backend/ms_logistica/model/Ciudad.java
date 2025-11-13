package com.backend.ms_logistica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CIUDAD")
public class Ciudad {
    //    ID_CIUDAD INT PRIMARY KEY AUTO_INCREMENT,
    //    NOMBRE VARCHAR(100),
    //    PROVINCIA VARCHAR(100)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CIUDAD")
    private Integer id_ciudad;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "PROVINCIA")
    private String provincia;

    public Ciudad() {
    }

    public Ciudad(String nombre, String provincia) {
        this.nombre = nombre;
        this.provincia = provincia;
    }

    public Integer getId_ciudad() {
        return id_ciudad;
    }

    public void setId_ciudad(Integer id_ciudad) {
        this.id_ciudad = id_ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
