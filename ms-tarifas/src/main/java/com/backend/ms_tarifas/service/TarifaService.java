package com.backend.ms_tarifas.service;

import com.backend.ms_tarifas.model.Tarifa;
import com.backend.ms_tarifas.repository.TarifaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifaService {

    private final TarifaRepository repo;

    public TarifaService(TarifaRepository repo) { this.repo = repo; }

    public List<Tarifa> findAll() { return repo.findAll(); }

    public Tarifa create(Tarifa tarifa) { return repo.save(tarifa); }
}
