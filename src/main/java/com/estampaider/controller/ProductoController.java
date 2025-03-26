package com.estampaider.controller;

import com.estampaider.service.ProductoService;
import com.estampaider.model.Producto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // Permite llamadas desde cualquier origen (útil para frontend)
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductos() {
        List<Producto> productos = productoService.getAllProductos();
        if (productos.isEmpty()) {
            logger.warn("No hay productos disponibles.");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.getProductoById(id);
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Producto con ID {} no encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        if (producto.getNombre() == null || producto.getPrecio() <= 0) {
            logger.error("Datos inválidos para crear el producto");
            return ResponseEntity.badRequest().build();
        }
        Producto nuevoProducto = productoService.saveProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoService.deleteProducto(id)) {
            logger.info("Producto con ID {} eliminado correctamente", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Intento de eliminar un producto inexistente con ID {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
