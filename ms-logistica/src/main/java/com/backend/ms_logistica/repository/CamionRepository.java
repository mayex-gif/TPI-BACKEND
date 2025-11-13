package com.backend.ms_logistica.repository;

import com.backend.ms_logistica.model.Camion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CamionRepository extends JpaRepository<Camion, String> {
}
