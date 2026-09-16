package com.backend.ms_clientes.service;

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

    public ContenedorService(ContenedorRepository contenedorRepository,
                             ClienteRepository clienteRepository,
                             EstadoRepository estadoRepository) {
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

    public Contenedor guardar(Contenedor contenedor) {
        return contenedorRepository.save(contenedor);
    }

    public void eliminar(Integer id) {
        contenedorRepository.deleteById(id);
    }

    public Cliente getClienteById(Integer id) {
        return clienteRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Cliente no encontrado con ID " + id));
    }

    public Estado getEstadoById(Integer id) {
        return estadoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Estado no encontrado con ID " + id));
    }
}
