package com.backend.ms_logistica.repository;

import com.backend.ms_logistica.model.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CamionRepository extends JpaRepository<Camion, String> {
    List<Camion> findByDisponible(boolean b);
}
