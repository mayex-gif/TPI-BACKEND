package com.backend.ms_logistica.service;

import com.backend.ms_logistica.dto.DepositoDTO;
import com.backend.ms_logistica.model.Deposito;
import com.backend.ms_logistica.repository.DepositoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepositoService {

    private final DepositoRepository depositoRepository;

    public DepositoService(DepositoRepository depositoRepository) {
        this.depositoRepository = depositoRepository;
    }

    // ======== OPERACIONES CRUD ========

    public List<DepositoDTO> obtenerTodos() {
        return depositoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DepositoDTO obtenerPorId(Integer id) {
        Deposito deposito = depositoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Depósito no encontrado con ID: " + id));
        return toDTO(deposito);
    }

    public DepositoDTO crear(DepositoDTO dto) {
        Deposito deposito = toEntity(dto);
        Deposito guardado = depositoRepository.save(deposito);
        return toDTO(guardado);
    }

    public DepositoDTO actualizar(Integer id, DepositoDTO dto) {
        Deposito deposito = depositoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Depósito no encontrado con ID: " + id));

        deposito.setNombre(dto.getNombre());
        deposito.setDireccion(dto.getDireccion());
        deposito.setLatitud(dto.getLatitud());
        deposito.setLongitud(dto.getLongitud());
        deposito.setCostoEstadiaDiario(dto.getCostoEstadiaDiario());

        Deposito actualizado = depositoRepository.save(deposito);
        return toDTO(actualizado);
    }

    public void eliminar(Integer id) {
        if (!depositoRepository.existsById(id)) {
            throw new RuntimeException("Depósito no encontrado con ID: " + id);
        }
        depositoRepository.deleteById(id);
    }

    // ======== MÉTODOS DE UTILIDAD ========

    public Deposito obtenerDepositoEntity(Integer id) {
        return depositoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Depósito no encontrado con ID: " + id));
    }

    public List<DepositoDTO> buscarCercanos(Double lat, Double lon, Double radioKm) {
        // Aquí podrías implementar búsqueda geoespacial
        // Por ahora retorna todos y filtras en memoria
        return depositoRepository.findAll().stream()
                .filter(d -> {
                    Double distancia = calcularDistancia(lat, lon, d.getLatitud(), d.getLongitud());
                    return distancia <= radioKm;
                })
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private Double calcularDistancia(Double lat1, Double lon1, Double lat2, Double lon2) {
        final int RADIO_TIERRA_KM = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) *
                        Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA_KM * c;
    }

    // ======== MAPPERS ========

    private DepositoDTO toDTO(Deposito deposito) {
        if (deposito == null) return null;

        return new DepositoDTO(
                deposito.getIdDeposito(),
                deposito.getNombre(),
                deposito.getDireccion(),
                deposito.getLatitud(),
                deposito.getLongitud(),
                deposito.getCostoEstadiaDiario()
        );
    }

    private Deposito toEntity(DepositoDTO dto) {
        if (dto == null) return null;

        Deposito deposito = new Deposito();
        deposito.setIdDeposito(dto.getIdDeposito());
        deposito.setNombre(dto.getNombre());
        deposito.setDireccion(dto.getDireccion());
        deposito.setLatitud(dto.getLatitud());
        deposito.setLongitud(dto.getLongitud());
        deposito.setCostoEstadiaDiario(dto.getCostoEstadiaDiario());

        return deposito;
    }
}