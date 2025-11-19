package com.backend.ms_logistica.repository;

import com.backend.ms_logistica.model.Estado;
import com.backend.ms_logistica.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
    List<Solicitud> findByIdCliente(Integer idCliente);

    List<Solicitud> findByEstado(Estado estado);
}
