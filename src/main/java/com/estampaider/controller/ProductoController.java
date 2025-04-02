package com.estampaider.controller;

import com.estampaider.service.ProductoService;
import com.estampaider.model.Producto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
// Permite llamadas desde cualquier origen (útil para frontend)
@RequestMapping("/api/productos")
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
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            logger.error("El nombre del producto no puede estar vacío.");
            return ResponseEntity.badRequest().build();
        }
        if (Objects.isNull(producto.getPrecio()) || producto.getPrecio() <= 0) {
            logger.error("El precio del producto debe ser mayor a 0.");
            return ResponseEntity.badRequest().build();
        }
        Producto nuevoProducto = productoService.saveProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id,
            @RequestBody Producto productoActualizado) {
        Optional<Producto> productoExistente = productoService.getProductoById(id);
        if (productoExistente.isPresent()) {
            Producto producto = productoService.actualizarProducto(id, productoActualizado);
            return ResponseEntity.ok(producto);
        } else {
            logger.warn("Intento de actualizar un producto inexistente con ID {}", id);
            return ResponseEntity.notFound().build();
        }
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
