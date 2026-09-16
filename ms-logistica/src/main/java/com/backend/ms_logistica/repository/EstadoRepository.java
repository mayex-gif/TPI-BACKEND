package com.backend.ms_logistica.repository;

import com.backend.ms_logistica.model.AmbitoEstado;
import com.backend.ms_logistica.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    Optional<Estado> findByNombreAndAmbito(String nombre, AmbitoEstado ambito);

    List<Estado> findByAmbito(AmbitoEstado ambito);
}
