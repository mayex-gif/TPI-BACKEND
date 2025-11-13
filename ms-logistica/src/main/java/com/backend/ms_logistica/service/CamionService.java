package com.backend.ms_logistica.service;

import com.backend.ms_logistica.model.Camion;
import com.backend.ms_logistica.repository.CamionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamionService {

    private final CamionRepository camionRepository;

    public CamionService(CamionRepository camionRepository) {
        this.camionRepository = camionRepository;
    }

    public List<Camion> listarTodos() {
        return camionRepository.findAll();
    }


}
