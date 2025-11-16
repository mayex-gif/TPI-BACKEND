-- =====================
-- CLIENTE
-- =====================
USE bd_clientes;
CREATE TABLE CLIENTE (
    ID_CLIENTE INT AUTO_INCREMENT PRIMARY KEY,
    NOMBRE VARCHAR(150) NOT NULL,
    APELLIDO VARCHAR(150) NOT NULL,
    DNI BIGINT UNIQUE NOT NULL,
    TELEFONO VARCHAR(50),
    EMAIL VARCHAR(150) UNIQUE,
    FECHA_REGISTRO DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

-- =====================
-- DATOS INICIALES
-- =====================
INSERT INTO CLIENTE (NOMBRE, APELLIDO, DNI, TELEFONO, EMAIL) VALUES
('Diego', 'Maradona', 12345678, '3511234567', 'diegote@gmail.com'),
('Lionel', 'Messi', 22345678, '3512234567', 'elgoat@gmail.com'),
('Pedro', 'Pascal', 32345678, '3513234567', 'pedrito@gmail.com');