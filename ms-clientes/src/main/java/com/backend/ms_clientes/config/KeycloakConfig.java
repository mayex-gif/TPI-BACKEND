package com.backend.ms_clientes.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    // --- VALORES HARDCODEADOS (PARA DEMOSTRACIÓN) ---
    // En producción, estos valores deberían provenir de application.yml por seguridad.
    private static final String AUTH_SERVER_URL = "http://localhost:8080/auth"; // URL base de su Keycloak
    public static final String REALM_NAME = "logistica-realm"; // Nombre de su Realm
    private static final String CLIENT_ID = "logistica-client"; // ID del cliente para el flujo 'client-credentials'
    private static final String CLIENT_SECRET = "logistica-secret-123"; // Secreto del cliente
    // -------------------------------------------------

    /**
     * Define el bean Keycloak que será inyectado en KeycloakServiceImpl para
     * realizar operaciones de administración (crear usuarios, asignar roles).
     * @return Cliente de administración de Keycloak.
     */
    @Bean
    public Keycloak keycloakAdminClient() {
        return KeycloakBuilder.builder()
                .serverUrl(AUTH_SERVER_URL)
                .realm(REALM_NAME)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }
}