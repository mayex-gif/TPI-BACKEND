package com.backend.ms_clientes.service;

import com.backend.ms_clientes.dto.ContenedorDTO;
import com.backend.ms_clientes.model.Cliente;
import com.backend.ms_clientes.model.Contenedor;
import com.backend.ms_clientes.model.Estado;
import com.backend.ms_clientes.repository.ClienteRepository;
import com.backend.ms_clientes.repository.ContenedorRepository;
import com.backend.ms_clientes.repository.EstadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContenedorService {

    private final ContenedorRepository contenedorRepository;
    private final ClienteRepository clienteRepository;
    private final EstadoRepository estadoRepository;

    public ContenedorService(ContenedorRepository contenedorRepository, ClienteRepository clienteRepository, EstadoRepository estadoRepository) {
        this.contenedorRepository = contenedorRepository;
        this.clienteRepository = clienteRepository;
        this.estadoRepository = estadoRepository;
    }

    public List<Contenedor> listarTodos() {
        return contenedorRepository.findAll();
    }

    public Contenedor obtenerPorId(Integer id) {
        return contenedorRepository.findById(id).orElse(null);
    }

    public Contenedor guardar(ContenedorDTO dto) {
        Estado estado = estadoRepository.findById(dto.getId_estado()).orElseThrow();
        Cliente cliente = clienteRepository.findById(dto.getId_cliente()).orElseThrow();

        Contenedor.Volumen volumenEnum;
        try {
            volumenEnum = Contenedor.Volumen.valueOf(dto.getVolumen().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Volumen inválido. Valores permitidos: KILOGRAMO, TONELADA, LIBRA");
        }

        Contenedor contenedor = new Contenedor(dto.getPeso(), volumenEnum, estado, cliente);
        return contenedorRepository.save(contenedor);
    }

    public void eliminar(Integer id) {
        contenedorRepository.deleteById(id);
    }

    public Contenedor actualizarEstado(Integer idContenedor, ContenedorDTO dto) {
        Contenedor contenedor = contenedorRepository.findById(idContenedor).orElseThrow();
        Estado estado = estadoRepository.findById(dto.getId_estado()).orElseThrow();

        contenedor.setEstado(estado);
        return contenedorRepository.save(contenedor);
    }
}
