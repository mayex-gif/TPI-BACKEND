package com.backend.ms_logistica.repository;

import com.backend.ms_logistica.model.Estado;
import com.backend.ms_logistica.model.Ruta;
import com.backend.ms_logistica.model.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TramoRepository extends JpaRepository<Tramo, Integer> {
    List<Tramo> findByRutaIdRuta(Integer idRuta);
    List<Tramo> findByEstado(Estado estadoIniciado);
}
