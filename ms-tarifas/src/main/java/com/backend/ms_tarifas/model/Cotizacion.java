package com.backend.ms_tarifas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COTIZACION")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COTIZACION")
    private Integer idCotizacion;
    @Column(name = "COSTO_CALCULADO")
    private Double costoCalculado;
}
