package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.OsrmResponseDTO; // <--- IMPORTANTE: Importar el DTO nuevo
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class OsrmService {

    @Value("${osrm.base-url:http://localhost:5000}")
    private String osrmBaseUrl;

    private final RestTemplate restTemplate;

    public OsrmService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Double> calcularMetricas(Double lat1, Double lon1, Double lat2, Double lon2) {

        // OSRM usa {longitud},{latitud}
        String url = UriComponentsBuilder
                .fromHttpUrl(osrmBaseUrl + "/route/v1/driving/{lon1},{lat1};{lon2},{lat2}")
                .queryParam("overview", "false")
                .queryParam("annotations", "duration,distance")
                .buildAndExpand(lon1, lat1, lon2, lat2)
                .toUriString();

        try {
            // 🚨 CORRECCIÓN AQUÍ: Usar OsrmResponseDTO.class (la clase pública separada)
            OsrmResponseDTO response = restTemplate.getForObject(url, OsrmResponseDTO.class);

            if (response == null || response.getRoutes() == null || response.getRoutes().isEmpty()) {
                // Asegúrate de tener la clase BadRequestException creada, o usa RuntimeException si aún no la creaste.
                throw new BadRequestException("No se pudo calcular la ruta con OSRM.");
            }

            // Acceder a los datos usando el DTO
            Double distanceMeters = response.getRoutes().get(0).getDistance();
            Double durationSeconds = response.getRoutes().get(0).getDuration();

            double distanceKm = distanceMeters / 1000.0;
            double durationHours = durationSeconds / 3600.0;

            return Map.of(
                    "distanceKm", distanceKm,
                    "durationHours", durationHours
            );

        } catch (BadRequestException e) {
            throw e; // Re-lanzar excepciones de negocio
        } catch (Exception e) {
            // Loguear el error real para debug (opcional)
            System.err.println("Error OSRM: " + e.getMessage());
            throw new RuntimeException("Error de comunicación con OSRM: " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class BadRequestException extends RuntimeException {

        public BadRequestException(String message) {
            super(message);
        }

        public BadRequestException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}