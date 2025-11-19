// DTO para asignar camión a un tramo
package com.backend.ms_logistica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AsignarCamionDTO {

    @NotNull(message = "El ID del tramo es obligatorio")
    private Integer idTramo;

    @NotBlank(message = "La patente del camión es obligatoria")
    private String patenteCamion;

    public AsignarCamionDTO() {}

    public AsignarCamionDTO(Integer idTramo, String patenteCamion) {
        this.idTramo = idTramo;
        this.patenteCamion = patenteCamion;
    }

    public Integer getIdTramo() { return idTramo; }
    public void setIdTramo(Integer idTramo) { this.idTramo = idTramo; }

    public String getPatenteCamion() { return patenteCamion; }
    public void setPatenteCamion(String patenteCamion) { this.patenteCamion = patenteCamion; }
}