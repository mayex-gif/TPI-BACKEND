package com.backend.ms_clientes.repository;

import com.backend.ms_clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByDni(Long dni);

    List<Cliente> findByApellidoContainingIgnoreCase(String apellido);

    Optional<Cliente> findByEmail(String email);
}
