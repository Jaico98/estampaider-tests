package com.estampaider.service.impl;

import com.estampaider.model.Producto;
import com.estampaider.repositories.ProductoRepository;
import com.estampaider.service.ProductoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de productos.
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param productoRepository Repositorio de productos.
     */
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Obtiene todos los productos almacenados en la base de datos.
     * 
     * @return Lista de productos.
     */
    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    /**
     * Busca un producto por su ID.
     * 
     * @param id ID del producto.
     * @return Un {@link Optional} con el producto si se encuentra, de lo contrario
     *         vacío.
     */
    @Override
    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    /**
     * Guarda o actualiza un producto en la base de datos.
     * 
     * @param producto Producto a guardar.
     * @return Producto guardado con su ID asignado.
     */
    @Override
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Elimina un producto de la base de datos si existe.
     * Usa `@Transactional` para asegurar que la operación se realice correctamente.
     * 
     * @param id ID del producto a eliminar.
     * @return {@code true} si el producto fue eliminado, {@code false} si no
     *         existe.
     */
    @Override
    @Transactional
    public boolean deleteProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Implementación de actualizarProducto()
    @Override
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setDescripcion(productoActualizado.getDescripcion());
            return productoRepository.save(producto);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }
}
