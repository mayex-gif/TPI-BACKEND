package com.backend.ms_logistica.repository;

import com.backend.ms_logistica.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
}
