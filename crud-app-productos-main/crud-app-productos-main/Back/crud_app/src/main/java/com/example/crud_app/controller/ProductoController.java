package com.example.crud_app.controller;

import com.example.crud_app.dto.CombinacionDTO;
import com.example.crud_app.modelo.Producto;
import com.example.crud_app.repository.ProductoRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping("/crear")
    @ApiOperation(value = "Crea un nuevo producto", notes = "Requiere nombre, descripción, precio y stock")
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Producto obtenerProducto(@PathVariable Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    @GetMapping("/todos")
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    @PutMapping("/actualizar/{id}")
    public Producto actualizarProducto(@PathVariable Integer id, @RequestBody Producto productoActualizado) {
        // Verifica si el producto existe
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        // Actualiza los campos del producto existente con los valores proporcionados
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setCantidad_en_stock(productoActualizado.getCantidad_en_stock());

        // Guarda el producto actualizado en la base de datos
        return productoRepository.save(productoExistente);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Integer id) {
        productoRepository.deleteById(id);
        return ResponseEntity.ok("Registro eliminado exitosamente");
    }

    @GetMapping("/valor-total-inventario")
    public double calcularValorTotalInventario() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().mapToDouble(producto -> producto.getPrecio() * producto.getCantidad_en_stock()).sum();
    }


    private double calcularSumaPrecios(List<Producto> productos) {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

    private List<String> obtenerNombresProductos(List<Producto> productos) {
        return productos.stream().map(Producto::getNombre).collect(Collectors.toList());
    }


    @GetMapping("/combinaciones/{valor}")
    public String obtenerCombinaciones(@PathVariable double valor) {
        List<Producto> productos = productoRepository.findAll();

        // Obtener todas las combinaciones posibles
        List<List<Producto>> combinaciones = obtenerTodasCombinaciones(productos);

        // Filtrar las combinaciones cuya suma de precios es menor o igual al valor ingresado
        List<CombinacionDTO> combinacionesFiltradas = combinaciones.stream()
                .filter(c -> calcularSumaPrecios(c) <= valor)
                .map(c -> new CombinacionDTO(obtenerNombresProductos(c), calcularSumaPrecios(c)))
                .sorted(Comparator.comparing(CombinacionDTO::getSumaPrecios).reversed())
                .limit(5)
                .collect(Collectors.toList());

        return "Hola desde Spring. Valor ingresado: " + valor;
        //return combinacionesFiltradas;
    }

    private List<List<Producto>> obtenerTodasCombinaciones(List<Producto> productos) {
        int n = productos.size();
        List<List<Producto>> result = new ArrayList<>();

        for (int i = 1; i < (1 << n); i++) {
            List<Producto> combinacion = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    combinacion.add(productos.get(j));
                }
            }
            result.add(combinacion);
        }

        return result;
    }




}
