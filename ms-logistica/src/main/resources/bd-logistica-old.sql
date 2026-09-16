USE bd_logistica;

-- ============================================================
--   TABLA ESTADO
-- ============================================================
CREATE TABLE ESTADO (
                        id_estado INT PRIMARY KEY AUTO_INCREMENT,
                        ambito VARCHAR(50) NOT NULL,  -- "SOLICITUD", "TRAMO", "CONTENEDOR"
                        nombre VARCHAR(50) NOT NULL,
                        descripcion VARCHAR(255)
);

-- ============================================================
--   TABLA DEPOSITO
-- ============================================================
CREATE TABLE DEPOSITO (
                          id_deposito INT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(150) NOT NULL,
                          direccion VARCHAR(255) NOT NULL,
                          latitud DOUBLE NOT NULL,
                          longitud DOUBLE NOT NULL,
                          costo_estadia_diario DOUBLE
);

-- ============================================================
--   TABLA CAMION
-- ============================================================
CREATE TABLE CAMION (
                        patente VARCHAR(20) PRIMARY KEY,
                        nombre_transportista VARCHAR(150) NOT NULL,
                        telefono VARCHAR(50),
                        capacidad_peso DOUBLE NOT NULL,
                        capacidad_volumen DOUBLE NOT NULL,
                        disponible BOOLEAN NOT NULL DEFAULT TRUE,
--                         costo_base_km DOUBLE NOT NULL,
                        consumo_combustible DOUBLE NOT NULL
);

-- ============================================================
--   TABLA SOLICITUD
-- ============================================================
CREATE TABLE SOLICITUD (
                           id_solicitud INT PRIMARY KEY AUTO_INCREMENT,
                           id_cliente INT,
                           id_estado INT NOT NULL,
                           fecha_solicitud DATETIME NOT NULL,
                           descripcion TEXT,

                           origen_lat DOUBLE NOT NULL,
                           origen_lon DOUBLE NOT NULL,
                           destino_lat DOUBLE NOT NULL,
                           destino_lon DOUBLE NOT NULL,

                           costo_estimado DOUBLE,
                           tiempo_estimado DOUBLE,
                           costo_final DOUBLE,
                           tiempo_real DOUBLE,

                           FOREIGN KEY (id_estado) REFERENCES ESTADO(id_estado)
);

-- ============================================================
--   TABLA RUTA
-- ============================================================
CREATE TABLE RUTA (
                      id_ruta INT PRIMARY KEY AUTO_INCREMENT,
                      id_solicitud INT NOT NULL,
                      FOREIGN KEY (id_solicitud) REFERENCES SOLICITUD(id_solicitud)
);

-- ============================================================
--   TABLA CONTENEDOR
-- ============================================================
CREATE TABLE CONTENEDOR (
                            id_contenedor INT PRIMARY KEY AUTO_INCREMENT,
                            id_solicitud INT NOT NULL,
                            peso DOUBLE,
                            volumen VARCHAR(20) NOT NULL,
                            id_estado INT NOT NULL,
                            FOREIGN KEY (id_solicitud) REFERENCES SOLICITUD(id_solicitud),
                            FOREIGN KEY (id_estado) REFERENCES ESTADO(id_estado)
);

-- ============================================================
--   TABLA TRAMO
-- ============================================================
CREATE TABLE TRAMO (
                       id_tramo INT PRIMARY KEY AUTO_INCREMENT,
                       id_ruta INT NOT NULL,
                       tipo_tramo VARCHAR(50) NOT NULL,  -- ORIGEN_DEPOSITO, DEPOSITO_DEPOSITO, DEPOSITO_DESTINO, ORIGEN_DESTINO

                       id_deposito_inicio INT,
                       id_deposito_fin INT,
                       inicio_lat DOUBLE,
                       inicio_lon DOUBLE,
                       fin_lat DOUBLE,
                       fin_lon DOUBLE,

                       id_estado INT NOT NULL,

                       distancia_km DOUBLE,
                       tiempo_estimado DOUBLE,
                       costo_estimado DOUBLE,

                       patente_camion VARCHAR(20),

                       FOREIGN KEY (id_ruta) REFERENCES RUTA(id_ruta),
                       FOREIGN KEY (id_estado) REFERENCES ESTADO(id_estado),
                       FOREIGN KEY (id_deposito_inicio) REFERENCES DEPOSITO(id_deposito),
                       FOREIGN KEY (id_deposito_fin) REFERENCES DEPOSITO(id_deposito),
                       FOREIGN KEY (patente_camion) REFERENCES CAMION(patente)
);

-- ============================================================
--   ESTADOS INICIALES
-- ============================================================
INSERT INTO ESTADO (ambito, nombre, descripcion) VALUES
-- SOLICITUD
('SOLICITUD', 'BORRADOR', 'Solicitud creada, sin planificación.'),
('SOLICITUD', 'PROGRAMADA', 'Ruta asignada y lista para ejecutarse.'),
('SOLICITUD', 'EN_TRANSITO', 'Solicitud ya está siendo transportada.'),
('SOLICITUD', 'ENTREGADA', 'Solicitud completada y contenedor liberado.'),

-- CONTENEDOR
('CONTENEDOR', 'EN_ORIGEN', 'Contenedor en el punto de origen.'),
('CONTENEDOR', 'EN_DEPOSITO', 'Contenedor dentro de un depósito.'),
('CONTENEDOR', 'EN_TRANSITO', 'Contenedor en traslado por un tramo.'),
('CONTENEDOR', 'EN_DESTINO', 'Contenedor llegó al destino final.'),
('CONTENEDOR', 'LIBRE', 'Contenedor disponible para reutilización.'),

-- TRAMO
('TRAMO', 'ESTIMADO', 'Tramo generado automáticamente (OSRM).'),
('TRAMO', 'ASIGNADO', 'Tramo ya tiene un recurso asignado.'),
('TRAMO', 'INICIADO', 'Tramo comenzó su ejecución.'),
('TRAMO', 'FINALIZADO', 'Tramo completado.');


-- ============================================================
--   DEPOSITOS INICIALES
-- ============================================================
INSERT INTO DEPOSITO (nombre, direccion, latitud, longitud, costo_estadia_diario) VALUES
                                                                                      ('Depósito Córdoba', 'Av. Siempre Viva 123', -31.4201, -64.1888, 5000),
                                                                                      ('Depósito Buenos Aires', 'Calle Falsa 742', -34.6037, -58.3816, 8000);

-- ============================================================
--   CAMIONES INICIALES
-- ============================================================
INSERT INTO CAMION (patente, nombre_transportista, telefono, capacidad_peso, capacidad_volumen, disponible, costo_base_km, consumo_combustible)
VALUES
    ('AA001AA', 'Juan Pérez', '3511234567', 10000, 40, TRUE, 300, 25),
    ('AB202CD', 'Carlos Gómez', '3517654321', 8000, 30, TRUE, 250, 20);

-- ============================================================
--   SOLICITUD DE EJEMPLO
-- ============================================================
INSERT INTO SOLICITUD (
    id_cliente, id_estado, descripcion,
    origen_lat, origen_lon, destino_lat, destino_lon,
    costo_estimado, tiempo_estimado
) VALUES
    (NULL, 1, 'Carga liviana desde Alta Gracia a Córdoba',
     -31.6589, -64.4280,   -- Alta Gracia
     -31.4201, -64.1888,   -- Córdoba
     0, 0
    );

-- ============================================================
--   RUTA ASOCIADA A LA SOLICITUD
-- ============================================================
INSERT INTO RUTA (id_solicitud)
VALUES (1);

-- ============================================================
--   TRAMOS INICIALES (ejemplo simple: un solo tramo ORIGEN_DESTINO)
-- ============================================================
INSERT INTO TRAMO (
    id_ruta, tipo_tramo,
    inicio_lat, inicio_lon, fin_lat, fin_lon,
    id_estado, distancia_km, tiempo_estimado, costo_estimado, patente_camion
) VALUES
    (1, 'ORIGEN_DESTINO',
     -31.6589, -64.4280,
     -31.4201, -64.1888,
     2, 35, 40, 12000, 'AA001AA');