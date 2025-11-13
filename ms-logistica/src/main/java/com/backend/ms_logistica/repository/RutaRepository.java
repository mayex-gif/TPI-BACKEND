package com.backend.ms_logistica.repository;

import com.backend.ms_logistica.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RutaRepository extends JpaRepository<Ruta, Integer> {
    List<Ruta> findByIdSolicitud(Integer idSolicitud);
}

