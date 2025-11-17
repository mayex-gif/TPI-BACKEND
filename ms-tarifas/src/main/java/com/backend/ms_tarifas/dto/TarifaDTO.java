package com.backend.ms_tarifas.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TarifaDTO {
    private Integer idTarifa;
    private Double precioBase;
    private Double costoPorKm;
}
