package com.backend.ms_tarifas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TARIFA")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TARIFA")
    private Integer idTarifa;

    @Column(name = "PRECIO_BASE")
    private Double precioBase;

    @Column(name = "COSTO_POR_KM")
    private Double costoPorKm;
}
