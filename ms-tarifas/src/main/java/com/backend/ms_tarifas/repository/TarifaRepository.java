package com.backend.ms_tarifas.repository;

import com.backend.ms_tarifas.model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {}
