package com.estampaider.repositories;

import com.estampaider.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la gestión de productos en la base de datos.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Encuentra productos por su nombre (búsqueda exacta).
     * 
     * @param nombre Nombre del producto a buscar.
     * @return Lista de productos que coincidan con el nombre.
     */
    List<Producto> findByNombre(String nombre);

    /**
     * Encuentra productos dentro de un rango de precios.
     * 
     * @param precioMin Precio mínimo.
     * @param precioMax Precio máximo.
     * @return Lista de productos cuyo precio está dentro del rango especificado.
     */
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioMin AND :precioMax")
    List<Producto> findByPrecioBetween(@Param("precioMin") double precioMin, @Param("precioMax") double precioMax);
}
