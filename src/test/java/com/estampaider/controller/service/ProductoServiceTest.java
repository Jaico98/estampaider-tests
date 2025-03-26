package com.estampaider.controller.service;

import com.estampaider.model.Producto;
import com.estampaider.repositories.ProductoRepository;
import com.estampaider.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void testObtenerProductosNoVacio() {
        // Simulamos una lista de productos
        List<Producto> productos = Arrays.asList(
                new Producto(1L, "Camiseta de algodón", 40000.0, "Camiseta 100% algodón."),
                new Producto(2L, "Mug 11 Oz", 19000.0, "Mug de cerámica personalizable."));

        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> resultado = productoService.getAllProductos();
        assertFalse(resultado.isEmpty(), "Error: La lista de productos no debería estar vacía.");
        assertEquals(2, resultado.size(), "Error: La cantidad de productos devueltos no coincide.");
    }

    @Test
    void testPrimerProducto() {
        // Simulamos una lista con productos
        List<Producto> productos = Arrays.asList(
                new Producto(1L, "Camiseta piel durazno", 30000.0, "Camiseta suave y cómoda."),
                new Producto(2L, "Mug Mágico", 22000.0, "Pocillo que revela tu diseño."));

        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> resultado = productoService.getAllProductos();
        assertEquals("Camiseta piel durazno", resultado.get(0).getNombre(),
                "Error: El primer producto debería ser 'Camiseta piel durazno'.");
    }

    @Test
    void testObtenerProductoPorId() {
        Producto producto = new Producto(1L, "Camiseta de algodón", 40000.0, "Camiseta 100% algodón.");
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = productoService.getProductoById(1L);

        assertTrue(resultado.isPresent(), "Error: El producto con ID 1 debería existir.");
        assertEquals("Camiseta de algodón", resultado.get().getNombre(),
                "Error: El nombre del producto no coincide.");
    }

    @Test
    void testCrearProducto() {
        Producto nuevoProducto = new Producto(null, "Gorra Snapback", 25000.0, "Gorra ajustable personalizada.");
        Producto productoGuardado = new Producto(1L, "Gorra Snapback", 25000.0, "Gorra ajustable personalizada.");

        when(productoRepository.save(any(Producto.class))).thenReturn(productoGuardado);

        Producto resultado = productoService.saveProducto(nuevoProducto);

        assertNotNull(resultado.getId(), "Error: El producto guardado debería tener un ID.");
        assertEquals("Gorra Snapback", resultado.getNombre(), "Error: El nombre del producto no coincide.");
    }

    @Test
    void testEliminarProducto() {
        // Simulamos que el producto con ID 1 existe
        when(productoRepository.existsById(1L)).thenReturn(true);

        // Simulamos la eliminación del producto
        doNothing().when(productoRepository).deleteById(1L);

        // Ejecutamos el método deleteProducto
        boolean eliminado = productoService.deleteProducto(1L);

        // Verificamos que el producto fue eliminado
        assertTrue(eliminado, "Error: El producto debería haberse eliminado.");

        // Verificamos que `deleteById(1L)` fue llamado una vez
        verify(productoRepository, times(1)).deleteById(1L);
    }
}
