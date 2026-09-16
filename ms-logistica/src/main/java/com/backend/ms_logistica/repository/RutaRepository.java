package com.backend.ms_logistica.repository;

import com.backend.ms_logistica.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Integer> {
    Optional<Ruta> findBySolicitudIdSolicitud(Integer idSolicitud);
}
