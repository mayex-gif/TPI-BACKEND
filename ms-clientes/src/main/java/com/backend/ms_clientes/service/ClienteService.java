package com.backend.ms_clientes.service;

import com.backend.ms_clientes.dto.ClienteDTO;
import com.backend.ms_clientes.model.Cliente;
import com.backend.ms_clientes.repository.ClienteRepository;
import com.backend.ms_clientes.repository.KeycloakInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private KeycloakInterface keycloakService;

    /**
     * Crea un nuevo cliente, registrándolo previamente en Keycloak y asignándole el rol CLIENTE.
     * @param dto El DTO con los datos del cliente, incluyendo la contraseña.
     * @return El DTO del cliente guardado.
     */
    @Transactional
    public ClienteDTO crear(ClienteDTO dto) {
        // 1. Validaciones de unicidad (DNI/Email)
        Optional<Cliente> existenteDni = repo.findByDni(dto.getDni());
        if (existenteDni.isPresent()) {
            throw new RuntimeException("Cliente con este DNI ya existe.");
        }
        Optional<Cliente> clienteEmail = repo.findByEmail(dto.getEmail());
        if (clienteEmail.isPresent()) {
            // Nota: Podrías necesitar buscar también por username si Keycloak lo requiere.
            throw new RuntimeException("Cliente con este EMAIL ya existe.");
        }

        // 2. Crear usuario en Keycloak y obtener su ID
        String keycloakUserId = keycloakService.crearUsuarioYAsignarRol(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                "CLIENTE"
        );

        if (keycloakUserId == null) {
            // Si falla la creación del usuario en Keycloak por un motivo no manejado
            throw new RuntimeException("Error fatal al crear el usuario en Keycloak.");
        }

        // 3. Mapear DTO a Entidad y asignar el ID de Keycloak
        Cliente c = toEntity(dto);
        c.setKeycloakId(keycloakUserId); // Asignamos el ID de Keycloak

        // 4. Guardar la entidad en la base de datos
        Cliente guardado = repo.save(c);

        // 5. Retornar el DTO (sin password ni Keycloak ID)
        return toDTO(guardado);
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
        // En un escenario real, actualizar el email requeriría actualizar Keycloak
        c.setEmail(nuevo.getEmail());
        return repo.save(c);
    }

    public void eliminar(Integer id) {
        // Lógica de negocio:
        // 1. Buscar el cliente para obtener su keycloakId
        // 2. Llamar a KeycloakService para eliminar el usuario por keycloakId
        // 3. Eliminar de la base de datos
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
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setDni(cliente.getDni()); // Usamos el Long directamente
        dto.setTelefono(cliente.getTelefono());
        dto.setEmail(cliente.getEmail());
        // No se mapea el password ni el keycloakId por seguridad
        return dto;
    }
}