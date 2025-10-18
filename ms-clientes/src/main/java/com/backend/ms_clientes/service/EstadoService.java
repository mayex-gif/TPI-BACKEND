package com.backend.ms_clientes.service;

import com.backend.ms_clientes.model.Contenedor;
import com.backend.ms_clientes.model.Estado;
import com.backend.ms_clientes.repository.EstadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> listarTodos() {
        return estadoRepository.findAll();
    }

    public Estado obtenerPorId(Integer id) {
        return estadoRepository.findById(id).orElse(null);
    }


}
