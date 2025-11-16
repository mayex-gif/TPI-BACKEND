package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.DepositoDTO;
import com.backend.ms_logistica.model.Deposito;
import com.backend.ms_logistica.repository.DepositoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepositoService {

    @Autowired
    private DepositoRepository repo;

    // Crear
    public Deposito crear(Deposito deposito) {
        return repo.save(deposito);
    }

    // Listar
    public List<Deposito> listar() {
        return repo.findAll();
    }

    // Buscar
    public Deposito buscar(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Depósito no encontrado: " + id));
    }

    // Actualizar
    public Deposito actualizar(Integer id, Deposito nuevo) {
        Deposito d = buscar(id);
        d.setNombre(nuevo.getNombre());
        d.setDireccion(nuevo.getDireccion());
        d.setLatitud(nuevo.getLatitud());
        d.setLongitud(nuevo.getLongitud());
        return repo.save(d);
    }

    // Eliminar
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // Mapper: Entity -> DTO
    public static DepositoDTO toDTO(Deposito deposito) {
        return new DepositoDTO(
                deposito.getIdDeposito(),
                deposito.getNombre(),
                deposito.getDireccion(),
                deposito.getLatitud(),
                deposito.getLongitud(),
                deposito.getCostoEstadiaDiario()
        );
    }

    // Mapper: DTO -> Entity
    public static Deposito toEntity(DepositoDTO dto) {
        Deposito d = new Deposito();
        d.setNombre(dto.getNombre());
        d.setDireccion(dto.getDireccion());
        d.setLatitud(dto.getLatitud());
        d.setLongitud(dto.getLongitud());
        d.setCostoEstadiaDiario(dto.getCostoEstadiaDiario());
        return d;
    }

    // List DTO
    public List<DepositoDTO> listarDTO() {
        return listar().stream()
                .map(DepositoService::toDTO)
                .collect(Collectors.toList());
    }
}
