-- ============================================================
--   TABLA ESTADO
-- ============================================================
USE bd_logistica;
CREATE TABLE ESTADO (
                        id_estado INT PRIMARY KEY AUTO_INCREMENT,
                        nombre VARCHAR(100) NOT NULL,
                        descripcion VARCHAR(255)
);

-- ============================================================
--   TABLA UBICACION (para orígenes/destinos)
-- ============================================================
CREATE TABLE UBICACION (
                           id_ubicacion INT PRIMARY KEY AUTO_INCREMENT,
                           nombre VARCHAR(150) NOT NULL,
                           direccion VARCHAR(255) NOT NULL,
                           latitud DOUBLE NOT NULL,
                           longitud DOUBLE NOT NULL
);

-- ============================================================
--   TABLA DEPOSITO (almacenes intermedios)
-- ============================================================
CREATE TABLE DEPOSITO (
                          id_deposito INT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(150) NOT NULL,
                          direccion VARCHAR(255) NOT NULL,
                          latitud DOUBLE NOT NULL,
                          longitud DOUBLE NOT NULL,
                          costo_estadia_diario DOUBLE DEFAULT 500.0
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
                        costo_base_km DOUBLE NOT NULL,
                        consumo_combustible DOUBLE NOT NULL
);

-- ============================================================
--   TABLA RUTA (rutas predefinidas)
-- ============================================================
CREATE TABLE RUTA (
                      id_ruta INT PRIMARY KEY AUTO_INCREMENT,
                      origen VARCHAR(255) NOT NULL,
                      destino VARCHAR(255) NOT NULL,
                      distancia_km DOUBLE NOT NULL,
                      costo_base DOUBLE NOT NULL
);

-- ============================================================
--   TABLA SOLICITUD
-- ============================================================
CREATE TABLE SOLICITUD (
                           id_solicitud INT PRIMARY KEY AUTO_INCREMENT,
                           id_cliente INT NOT NULL,
                           id_ruta INT,
                           id_estado INT NOT NULL,
                           fecha_solicitud DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           descripcion TEXT,
                           costo_estimado DOUBLE,
                           tiempo_estimado DOUBLE,
                           FOREIGN KEY (id_ruta) REFERENCES RUTA(id_ruta),
                           FOREIGN KEY (id_estado) REFERENCES ESTADO(id_estado)
);

-- ============================================================
--   TABLA CONTENEDOR
-- ============================================================
CREATE TABLE CONTENEDOR (
                            id_contenedor INT PRIMARY KEY AUTO_INCREMENT,
                            id_solicitud INT NOT NULL,
                            tipo VARCHAR(100) NOT NULL,
                            volumen VARCHAR(50) NOT NULL,
                            peso DOUBLE,
                            FOREIGN KEY (id_solicitud) REFERENCES SOLICITUD(id_solicitud)
);

-- ============================================================
--   TABLA TRAMO
-- ============================================================
CREATE TABLE TRAMO (
                       id_tramo INT PRIMARY KEY AUTO_INCREMENT,
                       id_ruta INT NOT NULL,
                       tipo_tramo VARCHAR(50) NOT NULL,  -- ORIGEN_DEPOSITO, DEPOSITO_DEPOSITO, etc.
                       id_origen INT NOT NULL,
                       id_destino INT NOT NULL,
                       id_estado INT NOT NULL,

                       distancia_km DOUBLE,
                       tiempo_estimado DOUBLE,

                       costo_aprox DOUBLE,
                       costo_real DOUBLE,
                       tiempo_real DOUBLE,

                       fecha_hora_inicio DATETIME,
                       fecha_hora_fin DATETIME,

                       patente_camion VARCHAR(20),

                       FOREIGN KEY (id_ruta) REFERENCES RUTA(id_ruta),
                       FOREIGN KEY (id_estado) REFERENCES ESTADO(id_estado),
                       FOREIGN KEY (patente_camion) REFERENCES CAMION(patente)
);

-- ============================================================
--   DATOS INICIALES
-- ============================================================

-- Estados básicos
INSERT INTO ESTADO (nombre, descripcion) VALUES
                                             ('Borrador', 'Solicitud en borrador'),
                                             ('Programada', 'Solicitud programada'),
                                             ('En Tránsito', 'Solicitud en tránsito'),
                                             ('Entregada', 'Solicitud entregada'),
                                             ('Cancelada', 'Solicitud cancelada');

-- Depósitos en Córdoba
INSERT INTO DEPOSITO (nombre, direccion, latitud, longitud, costo_estadia_diario) VALUES
                                                                                      ('Depósito Central Córdoba', 'Av. Circunvalación 1000', -31.4201, -64.1888, 500.0),
                                                                                      ('Depósito Alta Gracia', 'Ruta E55 Km 30', -31.6536, -64.4289, 300.0),
                                                                                      ('Depósito Carlos Paz', 'Av. San Martín 500', -31.4241, -64.4978, 400.0);

-- Ubicaciones comunes
INSERT INTO UBICACION (nombre, direccion, latitud, longitud) VALUES
                                                                 ('Centro Córdoba', 'Plaza San Martín', -31.4201, -64.1888),
                                                                 ('Nueva Córdoba', 'Av. Hipólito Yrigoyen', -31.4278, -64.1882),
                                                                 ('Aeropuerto Córdoba', 'Aeropuerto Internacional', -31.3156, -64.2080);

-- Camiones de ejemplo
INSERT INTO CAMION (patente, nombre_transportista, telefono, capacidad_peso, capacidad_volumen, disponible, costo_base_km, consumo_combustible) VALUES
                                                                                                                                                    ('ABC123', 'Juan Pérez', '351-1234567', 5000.0, 30.0, TRUE, 50.0, 15.0),
                                                                                                                                                    ('XYZ789', 'María González', '351-7654321', 8000.0, 50.0, TRUE, 70.0, 20.0),
                                                                                                                                                    ('DEF456', 'Carlos Rodríguez', '351-9876543', 3000.0, 20.0, TRUE, 40.0, 12.0);

-- Rutas comunes
INSERT INTO RUTA (origen, destino, distancia_km, costo_base) VALUES
                                                                 ('Córdoba', 'Alta Gracia', 35.0, 1500.0),
                                                                 ('Córdoba', 'Carlos Paz', 40.0, 1800.0),
                                                                 ('Alta Gracia', 'Carlos Paz', 60.0, 2500.0);