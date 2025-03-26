package com.estampaider.service;

import com.estampaider.model.Producto;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de productos.
 */
public interface ProductoService {

    /**
     * Obtiene todos los productos disponibles en la base de datos.
     * 
     * @return Lista de productos.
     */
    List<Producto> getAllProductos();

    /**
     * Busca un producto por su ID.
     * 
     * @param id ID del producto a buscar.
     * @return Un {@link Optional} con el producto si existe, de lo contrario vacío.
     */
    Optional<Producto> getProductoById(Long id);

    /**
     * Guarda o actualiza un producto en la base de datos.
     * 
     * @param producto Producto a guardar.
     * @return Producto guardado con su ID asignado.
     */
    Producto saveProducto(Producto producto);

    /**
     * Elimina un producto de la base de datos si existe.
     * 
     * @param id ID del producto a eliminar.
     * @return {@code true} si el producto fue eliminado, {@code false} si no se
     *         encontró.
     */
    boolean deleteProducto(Long id);
}
