package com.backend.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        // ==========================================
                        // ENDPOINTS PÚBLICOS (sin autenticación)
                        // ==========================================
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers("/api/v0.1/clientes/registro").permitAll() // Registro de clientes

                        // ==========================================
                        // ENDPOINTS POR ROL: CLIENTE
                        // ==========================================
                        .pathMatchers("GET", "/api/v0.1/solicitudes/cliente/{idCliente}").hasRole("CLIENTE")
                        .pathMatchers("POST", "/api/v0.1/solicitudes").hasRole("CLIENTE")
                        .pathMatchers("GET", "/api/v0.1/solicitudes/{id}/estado-transporte").hasRole("CLIENTE")

                        // ==========================================
                        // ENDPOINTS POR ROL: OPERADOR
                        // ==========================================
                        .pathMatchers("GET", "/api/v0.1/solicitudes/**").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .pathMatchers("PATCH", "/api/v0.1/solicitudes/{id}/asignar-ruta").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .pathMatchers("GET", "/api/v0.1/solicitudes/{id}/rutas-tentativas").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .pathMatchers("GET", "/api/v0.1/contenedores/**").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .pathMatchers("PATCH", "/api/v0.1/tramos/{id}/asignar-camion").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .pathMatchers("GET", "/api/v0.1/tramos/**").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .pathMatchers("GET", "/api/v0.1/camiones/disponibles").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .pathMatchers("GET", "/api/v0.1/camiones/buscar-compatibles").hasAnyRole("OPERADOR", "ADMINISTRADOR")

                        // ==========================================
                        // ENDPOINTS POR ROL: TRANSPORTISTA
                        // ==========================================
                        .pathMatchers("PATCH", "/api/v0.1/tramos/{id}/iniciar").hasRole("TRANSPORTISTA")
                        .pathMatchers("PATCH", "/api/v0.1/tramos/{id}/finalizar").hasRole("TRANSPORTISTA")
                        .pathMatchers("GET", "/api/v0.1/tramos/en-progreso").hasRole("TRANSPORTISTA")

                        // ==========================================
                        // ENDPOINTS POR ROL: ADMINISTRADOR
                        // ==========================================
                        .pathMatchers("POST", "/api/v0.1/camiones").hasRole("ADMINISTRADOR")
                        .pathMatchers("PUT", "/api/v0.1/camiones/**").hasRole("ADMINISTRADOR")
                        .pathMatchers("DELETE", "/api/v0.1/camiones/**").hasRole("ADMINISTRADOR")
                        .pathMatchers("POST", "/api/v0.1/depositos").hasRole("ADMINISTRADOR")
                        .pathMatchers("PUT", "/api/v0.1/depositos/**").hasRole("ADMINISTRADOR")
                        .pathMatchers("DELETE", "/api/v0.1/depositos/**").hasRole("ADMINISTRADOR")
                        .pathMatchers("POST", "/api/v0.1/estados").hasRole("ADMINISTRADOR")
                        .pathMatchers("PUT", "/api/v0.1/estados/**").hasRole("ADMINISTRADOR")
                        .pathMatchers("DELETE", "/api/v0.1/estados/**").hasRole("ADMINISTRADOR")
                        .pathMatchers("PATCH", "/api/v0.1/solicitudes/{id}/finalizar").hasRole("ADMINISTRADOR")
                        .pathMatchers("/api/v0.1/tarifas/**").hasRole("ADMINISTRADOR")

                        // ==========================================
                        // RESTO DE ENDPOINTS: REQUIEREN AUTENTICACIÓN
                        // ==========================================
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor()))
                );

        return http.build();
    }

    /**
     * Extrae los roles del token JWT de Keycloak
     * Los roles en Keycloak vienen en: resource_access.{client-id}.roles
     */
    @Bean
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}