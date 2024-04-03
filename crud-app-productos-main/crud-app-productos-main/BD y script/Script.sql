-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS crud_app;

-- Seleccionar la base de datos
USE crud_app;

-- Crear la tabla Producto
CREATE TABLE Producto (
    id INT PRIMARY KEY,
    nombre VARCHAR(255),
    descripcion TEXT,
    precio DECIMAL(10, 2),
    cantidad_en_stock INT
);


INSERT INTO Producto (id, nombre, descripcion, precio, cantidad_en_stock)
VALUES
    (1, 'escoba', 'escoba para barrer el piso', 12000, 56),
    (2, 'soflan suavitel', 'aromatizante', 15.000, 30),
    (3, 'arroz', 'aliemnto', 7.500, 75),
    (4, 'panela', 'alimento', 5.000, 120),
    (5, 'tequila', 'bebida', 180.000, 80);