package com.backend.ms_clientes.service;

import com.backend.ms_clientes.dto.ClienteDTO;
import com.backend.ms_clientes.model.Cliente;
import com.backend.ms_clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Cliente crear(Cliente c) {
        Optional<Cliente> existente = repo.findByDni(c.getDni());
        if (existente.isPresent()) {
            throw new RuntimeException("Cliente con este DNI ya existe");
        }
        Optional<Cliente> cliente = repo.findByEmail(c.getEmail());
        if (cliente.isPresent()) {
            throw new RuntimeException("Cliente con este EMAIL ya existe");
        }
        return repo.save(c);
    }


    public Cliente buscar(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public List<Cliente> listar() {
        return repo.findAll();
    }

    public Cliente actualizar(Integer id, Cliente nuevo) {
        Cliente c = buscar(id);
        c.setNombre(nuevo.getNombre());
        c.setApellido(nuevo.getApellido());
        c.setTelefono(nuevo.getTelefono());
        c.setEmail(nuevo.getEmail());
        return repo.save(c);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    public static Cliente toEntity(ClienteDTO dto) {
        return new Cliente(
                dto.getNombre(),
                dto.getApellido(),
                dto.getDni(),
                dto.getTelefono(),
                dto.getEmail()
        );
    }

    public static ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente()); // <--- ahora lo asigna
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setDni(cliente.getDni().longValue()); // si cambiaste a long en DTO
        dto.setTelefono(cliente.getTelefono());
        dto.setEmail(cliente.getEmail());
        return dto;
    }
}
