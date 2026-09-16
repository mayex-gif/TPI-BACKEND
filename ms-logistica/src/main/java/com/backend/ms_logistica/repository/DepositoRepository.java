package com.backend.ms_logistica.repository;

import com.backend.ms_logistica.model.Deposito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositoRepository extends JpaRepository<Deposito, Integer> {
}
