package com.backend.ms_logistica.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

// @JsonIgnoreProperties ignora campos extra que OSRM mande y no nos interesen
@JsonIgnoreProperties(ignoreUnknown = true)
public class OsrmResponseDTO {

    private List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    // Clase estática interna para Route, o podrías sacarla a otro archivo también.
    // Al estar en un archivo separado, esta debe ser estática si es interna.
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Route {
        private Double distance;
        private Double duration;

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public Double getDuration() {
            return duration;
        }

        public void setDuration(Double duration) {
            this.duration = duration;
        }
    }
}