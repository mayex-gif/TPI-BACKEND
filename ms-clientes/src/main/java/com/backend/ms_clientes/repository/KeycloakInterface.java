package com.backend.ms_clientes.repository;

public interface KeycloakInterface {
    /**
     * Crea un nuevo usuario en Keycloak, le asigna el rol "CLIENTE" y
     * retorna el ID único generado por Keycloak.
     * * @param email El email/username del nuevo usuario.
     * @param password La contraseña del nuevo usuario.
     * @param roleName El rol a asignar (debería ser "CLIENTE").
     * @return El ID del usuario creado en Keycloak, o null si falla.
     */
    String crearUsuarioYAsignarRol(String username, String email, String password, String roleName);
}

