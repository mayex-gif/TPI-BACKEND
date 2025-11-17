package com.backend.ms_tarifas.service;

import com.backend.ms_tarifas.model.Cotizacion;
import com.backend.ms_tarifas.repository.CotizacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CotizacionService {

    private final CotizacionRepository repo;

    public CotizacionService(CotizacionRepository repo) { this.repo = repo; }

    public List<Cotizacion> findAll() { return repo.findAll(); }

    public Cotizacion create(Cotizacion cot) { return repo.save(cot); }
}
