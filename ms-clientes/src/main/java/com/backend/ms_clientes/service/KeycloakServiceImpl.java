package com.backend.ms_clientes.service;

import com.backend.ms_clientes.config.KeycloakConfig; // <-- IMPORTAR CONSTANTE REALM_NAME
import com.backend.ms_clientes.repository.KeycloakInterface;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class KeycloakServiceImpl implements KeycloakInterface {

    private static final Logger log = LoggerFactory.getLogger(KeycloakServiceImpl.class);

    // Cliente inyectado para realizar operaciones de administración en Keycloak
    private final Keycloak keycloakAdminClient;

    // Se usa la constante definida en KeycloakConfig en lugar de @Value
    private final String realmName = KeycloakConfig.REALM_NAME;

    public KeycloakServiceImpl(Keycloak keycloakAdminClient) {
        this.keycloakAdminClient = keycloakAdminClient;
    }

    /**
     * Crea un usuario real en Keycloak con el username proporcionado y le asigna el rol especificado.
     * * @param username El nombre de usuario único para el login.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña.
     * @param roleName El nombre del rol a asignar (e.g., "cliente").
     * @return El ID (UUID) del usuario creado en Keycloak.
     * @throws RuntimeException Si la creación o asignación del rol falla.
     */
    @Override
    public String crearUsuarioYAsignarRol(String username, String email, String password, String roleName) {

        // 1. Crear la representación de las credenciales (contraseña)
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false); // La contraseña no expira
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);

        // 2. Crear la representación del usuario
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username); // *** CLAVE: Usamos el username para el login ***
        user.setEmail(email);
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(credential));

        // 3. Intento de creación del usuario en Keycloak
        try (Response response = keycloakAdminClient.realm(realmName).users().create(user)) {

            if (response.getStatus() != 201) {
                // Manejo de errores (ej. 409 Conflict: usuario/email ya existe)
                String errorDetails = response.readEntity(String.class);
                log.error("Error al crear usuario '{}' en Keycloak. Status: {}. Detalles: {}", username, response.getStatus(), errorDetails);
                throw new RuntimeException("Fallo al crear el usuario en Keycloak. Detalles: " + errorDetails);
            }

            // 4. Obtener el ID del nuevo usuario desde la cabecera 'Location'
            String path = response.getHeaderString("Location");
            String keycloakUserId = path.substring(path.lastIndexOf('/') + 1);

            log.info("Usuario '{}' creado exitosamente. ID Keycloak: {}", username, keycloakUserId);

            // 5. Asignar el Rol al usuario
            asignarRolAUsuario(keycloakUserId, roleName);

            return keycloakUserId;

        } catch (RuntimeException e) {
            // Re-lanzar errores de runtime (como usuario ya existente o rol no encontrado)
            throw e;
        } catch (Exception e) {
            // Capturar y manejar errores de conexión o I/O
            log.error("Error inesperado durante la comunicación con Keycloak: {}", e.getMessage());
            throw new RuntimeException("Error inesperado en el servicio Keycloak.", e);
        }
    }

    /**
     * Función auxiliar para buscar el rol por nombre y asignarlo al usuario.
     */
    private void asignarRolAUsuario(String keycloakUserId, String roleName) {

        // 5a. Buscar la representación del Rol en el Realm
        List<RoleRepresentation> roles = keycloakAdminClient.realm(realmName).roles().list();
        RoleRepresentation roleToAssign = roles.stream()
                .filter(r -> r.getName().equalsIgnoreCase(roleName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("El rol '" + roleName + "' no existe en el realm de Keycloak."));

        // 5b. Obtener el recurso del usuario y asignar el rol de Realm
        UserResource userResource = keycloakAdminClient.realm(realmName).users().get(keycloakUserId);
        userResource.roles().realmLevel().add(Collections.singletonList(roleToAssign));

        log.info("Rol '{}' asignado correctamente al usuario con ID: {}", roleName, keycloakUserId);
    }
}