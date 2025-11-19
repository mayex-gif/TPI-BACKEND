package com.backend.ms_logistica.repository;

import com.backend.ms_logistica.model.Contenedor;
import com.backend.ms_logistica.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContenedorRepository extends JpaRepository<Contenedor, Integer> {
    List<Contenedor> findByEstado(Estado estado);

    Optional<Contenedor> findBySolicitudIdSolicitud(Integer idSolicitud);
}
