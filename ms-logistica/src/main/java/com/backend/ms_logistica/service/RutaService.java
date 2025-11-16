package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.RutaDTO;
import com.backend.ms_logistica.model.Ruta;
import com.backend.ms_logistica.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RutaService {

    @Autowired
    private RutaRepository repo;

    public Ruta crear(Ruta ruta) {
        return repo.save(ruta);
    }

    public Ruta buscar(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada: " + id));
    }

    public List<Ruta> listar() {
        Iterable<Ruta> rutas = repo.findAll();
        List<Ruta> lista = new ArrayList<>();
        rutas.forEach(lista::add);
        return lista;
    }

    public Ruta actualizar(Integer id, Ruta nuevo) {
        Ruta r = buscar(id);
        r.setOrigen(nuevo.getOrigen());
        r.setDestino(nuevo.getDestino());
        r.setDistanciaKm(nuevo.getDistanciaKm());
        r.setCostoBase(nuevo.getCostoBase());
        return repo.save(r);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // Mapeo DTO
    public Ruta toEntity(RutaDTO dto) {
        Ruta r = new Ruta();
        r.setOrigen(dto.getOrigen());
        r.setDestino(dto.getDestino());
        r.setDistanciaKm(dto.getDistanciaKm());
        r.setCostoBase(dto.getCostoBase());
        return r;
    }

    public RutaDTO toDTO(Ruta r) {
        RutaDTO dto = new RutaDTO();
        dto.setIdRuta(r.getIdRuta());
        dto.setOrigen(r.getOrigen());
        dto.setDestino(r.getDestino());
        dto.setDistanciaKm(r.getDistanciaKm());
        dto.setCostoBase(r.getCostoBase());
        return dto;
    }
}
