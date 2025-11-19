-- ============================================================
--   BASE DE DATOS: bd_logistica
--   Sistema de Gestión de Transporte de Contenedores
--   Versión: 2.0 (Corregida y Completa)
-- ============================================================

-- DROP DATABASE IF EXISTS bd_logistica;
-- CREATE DATABASE bd_logistica CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_logistica;

-- ============================================================
--   TABLA: ESTADO
--   Estados para Solicitudes, Tramos y Contenedores
-- ============================================================
CREATE TABLE ESTADO (
                        id_estado INT PRIMARY KEY AUTO_INCREMENT,
                        ambito VARCHAR(50) NOT NULL COMMENT 'SOLICITUD, TRAMO, CONTENEDOR',
                        nombre VARCHAR(50) NOT NULL,
                        descripcion VARCHAR(255),
                        INDEX idx_estado_ambito (ambito)
) ENGINE=InnoDB;

-- ============================================================
--   TABLA: DEPOSITO
--   Puntos intermedios de almacenamiento temporal
-- ============================================================
CREATE TABLE DEPOSITO (
                          id_deposito INT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(150) NOT NULL,
                          direccion VARCHAR(255) NOT NULL,
                          latitud DOUBLE NOT NULL,
                          longitud DOUBLE NOT NULL,
                          costo_estadia_diario DOUBLE COMMENT 'Costo por día de almacenamiento',
                          INDEX idx_deposito_coords (latitud, longitud)
) ENGINE=InnoDB;

-- ============================================================
--   TABLA: CAMION
--   Vehículos de transporte
-- ============================================================
CREATE TABLE CAMION (
                        patente VARCHAR(20) PRIMARY KEY,
                        nombre_transportista VARCHAR(150) NOT NULL,
                        telefono VARCHAR(50),
                        capacidad_peso DOUBLE NOT NULL COMMENT 'Capacidad en kilogramos',
                        capacidad_volumen DOUBLE NOT NULL COMMENT 'Capacidad en metros cúbicos',
                        disponible BOOLEAN NOT NULL DEFAULT TRUE,
                        costo_base_km DOUBLE NOT NULL COMMENT 'Costo base por kilómetro',
                        consumo_combustible DOUBLE NOT NULL COMMENT 'Litros por kilómetro',
                        INDEX idx_camion_disponible (disponible)
) ENGINE=InnoDB;

-- ============================================================
--   TABLA: SOLICITUD
--   Solicitudes de transporte de contenedores
-- ============================================================
CREATE TABLE SOLICITUD (
                           id_solicitud INT PRIMARY KEY AUTO_INCREMENT,
                           id_cliente INT NOT NULL COMMENT 'ID del cliente en ms-clientes',
                           id_estado INT NOT NULL,
                           fecha_solicitud DATETIME NOT NULL,
                           descripcion TEXT,

    -- Origen
                           origen_direccion VARCHAR(255),
                           origen_lat DOUBLE NOT NULL,
                           origen_lon DOUBLE NOT NULL,

    -- Destino
                           destino_direccion VARCHAR(255),
                           destino_lat DOUBLE NOT NULL,
                           destino_lon DOUBLE NOT NULL,

    -- Costos y tiempos
                           costo_estimado DOUBLE COMMENT 'Costo calculado antes de iniciar',
                           tiempo_estimado DOUBLE COMMENT 'Tiempo estimado en horas',
                           costo_final DOUBLE COMMENT 'Costo real después de finalizar',
                           tiempo_real DOUBLE COMMENT 'Tiempo real en horas',

                           FOREIGN KEY (id_estado) REFERENCES ESTADO(id_estado),
                           INDEX idx_solicitud_cliente (id_cliente),
                           INDEX idx_solicitud_estado (id_estado),
                           INDEX idx_solicitud_fecha (fecha_solicitud)
) ENGINE=InnoDB;

-- ============================================================
--   TABLA: CONTENEDOR
--   Contenedores a transportar (1 por solicitud)
-- ============================================================
CREATE TABLE CONTENEDOR (
                            id_contenedor INT PRIMARY KEY AUTO_INCREMENT,
                            id_solicitud INT NOT NULL UNIQUE COMMENT 'Relación 1:1 con solicitud',
                            peso DOUBLE NOT NULL COMMENT 'Peso del contenedor',
                            unidad_peso VARCHAR(20) NOT NULL COMMENT 'KILOGRAMO, TONELADA, LIBRA',
                            volumen_m3 DOUBLE NOT NULL COMMENT 'Volumen en metros cúbicos',
                            id_estado INT NOT NULL,

                            FOREIGN KEY (id_solicitud) REFERENCES SOLICITUD(id_solicitud) ON DELETE CASCADE,
                            FOREIGN KEY (id_estado) REFERENCES ESTADO(id_estado),
                            INDEX idx_contenedor_estado (id_estado)
) ENGINE=InnoDB;

-- ============================================================
--   TABLA: RUTA
--   Ruta asignada a una solicitud
-- ============================================================
CREATE TABLE RUTA (
                      id_ruta INT PRIMARY KEY AUTO_INCREMENT,
                      id_solicitud INT NOT NULL UNIQUE COMMENT 'Relación 1:1 con solicitud',

                      FOREIGN KEY (id_solicitud) REFERENCES SOLICITUD(id_solicitud) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================================================
--   TABLA: TRAMO
--   Segmentos de una ruta
-- ============================================================
CREATE TABLE TRAMO (
                       id_tramo INT PRIMARY KEY AUTO_INCREMENT,
                       id_ruta INT NOT NULL,
                       tipo_tramo VARCHAR(50) NOT NULL COMMENT 'ORIGEN_DEPOSITO, DEPOSITO_DEPOSITO, DEPOSITO_DESTINO, ORIGEN_DESTINO',

    -- Depósitos opcionales (según tipo de tramo)
                       id_deposito_inicio INT,
                       id_deposito_fin INT,

    -- Coordenadas alternativas (cuando no hay depósito)
                       inicio_lat DOUBLE,
                       inicio_lon DOUBLE,
                       fin_lat DOUBLE,
                       fin_lon DOUBLE,

                       id_estado INT NOT NULL,

    -- Métricas
                       distancia_km DOUBLE,
                       tiempo_estimado DOUBLE COMMENT 'Tiempo estimado en horas',
                       costo_estimado DOUBLE,
                       costo_real DOUBLE COMMENT 'Costo real después de finalizar',

    -- Registro de ejecución
                       fecha_hora_inicio DATETIME,
                       fecha_hora_fin DATETIME,

    -- Camión asignado
                       patente_camion VARCHAR(20),

                       FOREIGN KEY (id_ruta) REFERENCES RUTA(id_ruta) ON DELETE CASCADE,
                       FOREIGN KEY (id_estado) REFERENCES ESTADO(id_estado),
                       FOREIGN KEY (id_deposito_inicio) REFERENCES DEPOSITO(id_deposito),
                       FOREIGN KEY (id_deposito_fin) REFERENCES DEPOSITO(id_deposito),
                       FOREIGN KEY (patente_camion) REFERENCES CAMION(patente),
                       INDEX idx_tramo_ruta (id_ruta),
                       INDEX idx_tramo_estado (id_estado),
                       INDEX idx_tramo_camion (patente_camion)
) ENGINE=InnoDB;

-- ============================================================
--   INSERCIÓN DE DATOS: ESTADOS
-- ============================================================

-- Estados de SOLICITUD
INSERT INTO ESTADO (ambito, nombre, descripcion) VALUES
                                                     ('SOLICITUD', 'BORRADOR', 'Solicitud creada, sin planificación de ruta'),
                                                     ('SOLICITUD', 'PROGRAMADA', 'Ruta asignada y lista para ejecutarse'),
                                                     ('SOLICITUD', 'EN_TRANSITO', 'Solicitud en proceso de transporte'),
                                                     ('SOLICITUD', 'ENTREGADA', 'Solicitud completada y contenedor entregado'),
                                                     ('SOLICITUD', 'CANCELADA', 'Solicitud cancelada por el cliente');

-- Estados de CONTENEDOR
INSERT INTO ESTADO (ambito, nombre, descripcion) VALUES
                                                     ('CONTENEDOR', 'EN_ORIGEN', 'Contenedor esperando retiro en origen'),
                                                     ('CONTENEDOR', 'EN_TRANSITO', 'Contenedor siendo transportado en un tramo'),
                                                     ('CONTENEDOR', 'EN_DEPOSITO', 'Contenedor almacenado temporalmente en depósito'),
                                                     ('CONTENEDOR', 'EN_DESTINO', 'Contenedor entregado en destino final'),
                                                     ('CONTENEDOR', 'LIBRE', 'Contenedor disponible para reutilización');

-- Estados de TRAMO
INSERT INTO ESTADO (ambito, nombre, descripcion) VALUES
                                                     ('TRAMO', 'ESTIMADO', 'Tramo planificado pero sin recursos asignados'),
                                                     ('TRAMO', 'ASIGNADO', 'Tramo con camión asignado, pendiente de inicio'),
                                                     ('TRAMO', 'INICIADO', 'Tramo en ejecución actualmente'),
                                                     ('TRAMO', 'FINALIZADO', 'Tramo completado exitosamente'),
                                                     ('TRAMO', 'CANCELADO', 'Tramo cancelado por incidencia');

-- ============================================================
--   INSERCIÓN DE DATOS: DEPOSITOS
-- ============================================================

INSERT INTO DEPOSITO (nombre, direccion, latitud, longitud, costo_estadia_diario) VALUES
                                                                                      ('Depósito Córdoba Centro', 'Av. Colón 1250, Córdoba Capital', -31.4201, -64.1888, 5000),
                                                                                      ('Depósito Córdoba Norte', 'Ruta Nacional 9 Km 695, Córdoba', -31.3500, -64.1800, 4500),
                                                                                      ('Depósito Buenos Aires', 'Av. General Paz 12500, CABA', -34.6037, -58.3816, 8000),
                                                                                      ('Depósito Rosario', 'Av. Circunvalación 8500, Rosario', -32.9442, -60.6505, 6000),
                                                                                      ('Depósito Mendoza', 'Ruta Nacional 7 Km 1055, Mendoza', -32.8895, -68.8458, 5500);

-- ============================================================
--   INSERCIÓN DE DATOS: CAMIONES
-- ============================================================

INSERT INTO CAMION (patente, nombre_transportista, telefono, capacidad_peso, capacidad_volumen, disponible, costo_base_km, consumo_combustible) VALUES
                                                                                                                                                    ('AA001AA', 'Juan Pérez', '351-1234567', 10000, 40, TRUE, 300, 0.25),
                                                                                                                                                    ('AB202CD', 'Carlos Gómez', '351-7654321', 8000, 30, TRUE, 250, 0.20),
                                                                                                                                                    ('AC303EF', 'María Rodríguez', '351-9876543', 12000, 50, TRUE, 350, 0.30),
                                                                                                                                                    ('AD404GH', 'Roberto Silva', '351-5555555', 6000, 25, TRUE, 200, 0.18),
                                                                                                                                                    ('AE505IJ', 'Laura Martínez', '351-4444444', 15000, 60, FALSE, 400, 0.35);

-- ============================================================
--   INSERCIÓN DE DATOS: SOLICITUDES DE EJEMPLO
-- ============================================================

-- Solicitud 1: Alta Gracia → Córdoba (sin depósitos)
INSERT INTO SOLICITUD (
    id_cliente, id_estado, fecha_solicitud, descripcion,
    origen_direccion, origen_lat, origen_lon,
    destino_direccion, destino_lat, destino_lon,
    costo_estimado, tiempo_estimado
) VALUES (
             101, 1, NOW(), 'Transporte de mercadería liviana desde Alta Gracia a Córdoba Capital',
             'Alta Gracia, Córdoba', -31.6589, -64.4280,
             'Córdoba Capital, Córdoba', -31.4201, -64.1888,
             10500, 0.75
         );

-- Solicitud 2: Córdoba → Buenos Aires (con 1 depósito)
INSERT INTO SOLICITUD (
    id_cliente, id_estado, fecha_solicitud, descripcion,
    origen_direccion, origen_lat, origen_lon,
    destino_direccion, destino_lat, destino_lon,
    costo_estimado, tiempo_estimado
) VALUES (
             102, 2, DATE_SUB(NOW(), INTERVAL 2 DAY), 'Transporte de equipo industrial pesado',
             'Córdoba Capital, Córdoba', -31.4201, -64.1888,
             'Buenos Aires, CABA', -34.6037, -58.3816,
             85000, 8.5
         );

-- Solicitud 3: Buenos Aires → Mendoza (con 2 depósitos)
INSERT INTO SOLICITUD (
    id_cliente, id_estado, fecha_solicitud, descripcion,
    origen_direccion, origen_lat, origen_lon,
    destino_direccion, destino_lat, destino_lon,
    costo_estimado, tiempo_estimado
) VALUES (
             103, 3, DATE_SUB(NOW(), INTERVAL 5 DAY), 'Transporte de productos refrigerados',
             'Buenos Aires, CABA', -34.6037, -58.3816,
             'Mendoza Capital, Mendoza', -32.8895, -68.8458,
             120000, 12
         );

-- ============================================================
--   INSERCIÓN DE DATOS: CONTENEDORES
-- ============================================================

INSERT INTO CONTENEDOR (id_solicitud, peso, unidad_peso, volumen_m3, id_estado) VALUES
                                                                                    (1, 5000, 'KILOGRAMO', 20, 6),  -- EN_ORIGEN
                                                                                    (2, 8.5, 'TONELADA', 35, 7),    -- EN_TRANSITO
                                                                                    (3, 12, 'TONELADA', 45, 8);     -- EN_DEPOSITO

-- ============================================================
--   INSERCIÓN DE DATOS: RUTAS
-- ============================================================

INSERT INTO RUTA (id_solicitud) VALUES
                                    (1),
                                    (2),
                                    (3);

-- ============================================================
--   INSERCIÓN DE DATOS: TRAMOS
-- ============================================================

-- RUTA 1: Alta Gracia → Córdoba (directo, sin depósitos)
INSERT INTO TRAMO (
    id_ruta, tipo_tramo,
    inicio_lat, inicio_lon, fin_lat, fin_lon,
    id_estado, distancia_km, tiempo_estimado, costo_estimado,
    patente_camion
) VALUES (
             1, 'ORIGEN_DESTINO',
             -31.6589, -64.4280,  -- Alta Gracia
             -31.4201, -64.1888,  -- Córdoba
             12, 35, 0.75, 10500,
             'AA001AA'
         );

-- RUTA 2: Córdoba → Rosario → Buenos Aires
-- Tramo 1: Córdoba → Depósito Rosario
INSERT INTO TRAMO (
    id_ruta, tipo_tramo,
    inicio_lat, inicio_lon,
    id_deposito_fin,
    fin_lat, fin_lon,
    id_estado, distancia_km, tiempo_estimado, costo_estimado,
    patente_camion, fecha_hora_inicio, fecha_hora_fin, costo_real
) VALUES (
             2, 'ORIGEN_DEPOSITO',
             -31.4201, -64.1888,  -- Córdoba
             4,  -- Depósito Rosario
             -32.9442, -60.6505,
             14, 320, 4, 80000,
             'AB202CD',
             DATE_SUB(NOW(), INTERVAL 2 DAY),
             DATE_SUB(NOW(), INTERVAL 1 DAY),
             82000
         );

-- Tramo 2: Depósito Rosario → Buenos Aires
INSERT INTO TRAMO (
    id_ruta, tipo_tramo,
    id_deposito_inicio,
    inicio_lat, inicio_lon,
    fin_lat, fin_lon,
    id_estado, distancia_km, tiempo_estimado, costo_estimado,
    patente_camion, fecha_hora_inicio
) VALUES (
             2, 'DEPOSITO_DESTINO',
             4,  -- Depósito Rosario
             -32.9442, -60.6505,
             -34.6037, -58.3816,  -- Buenos Aires
             13, 300, 4.5, 75000,
             'AB202CD',
             DATE_SUB(NOW(), INTERVAL 1 DAY)
         );

-- RUTA 3: Buenos Aires → Rosario → Córdoba → Mendoza
-- Tramo 1: Buenos Aires → Depósito Rosario
INSERT INTO TRAMO (
    id_ruta, tipo_tramo,
    inicio_lat, inicio_lon,
    id_deposito_fin,
    fin_lat, fin_lon,
    id_estado, distancia_km, tiempo_estimado, costo_estimado,
    patente_camion, fecha_hora_inicio, fecha_hora_fin, costo_real
) VALUES (
             3, 'ORIGEN_DEPOSITO',
             -34.6037, -58.3816,  -- Buenos Aires
             4,  -- Depósito Rosario
             -32.9442, -60.6505,
             14, 300, 4, 105000,
             'AC303EF',
             DATE_SUB(NOW(), INTERVAL 5 DAY),
             DATE_SUB(NOW(), INTERVAL 4 DAY),
             108000
         );

-- Tramo 2: Depósito Rosario → Depósito Córdoba
INSERT INTO TRAMO (
    id_ruta, tipo_tramo,
    id_deposito_inicio, id_deposito_fin,
    inicio_lat, inicio_lon,
    fin_lat, fin_lon,
    id_estado, distancia_km, tiempo_estimado, costo_estimado,
    patente_camion, fecha_hora_inicio, fecha_hora_fin, costo_real
) VALUES (
             3, 'DEPOSITO_DEPOSITO',
             4, 1,  -- Rosario → Córdoba
             -32.9442, -60.6505,
             -31.4201, -64.1888,
             14, 320, 4, 112000,
             'AC303EF',
             DATE_SUB(NOW(), INTERVAL 4 DAY),
             DATE_SUB(NOW(), INTERVAL 3 DAY),
             115000
         );

-- Tramo 3: Depósito Córdoba → Mendoza (en progreso)
INSERT INTO TRAMO (
    id_ruta, tipo_tramo,
    id_deposito_inicio,
    inicio_lat, inicio_lon,
    fin_lat, fin_lon,
    id_estado, distancia_km, tiempo_estimado, costo_estimado,
    patente_camion, fecha_hora_inicio
) VALUES (
             3, 'DEPOSITO_DESTINO',
             1,  -- Depósito Córdoba
             -31.4201, -64.1888,
             -32.8895, -68.8458,  -- Mendoza
             13, 700, 8, 245000,
             'AC303EF',
             DATE_SUB(NOW(), INTERVAL 2 DAY)
         );