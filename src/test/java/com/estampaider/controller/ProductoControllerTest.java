package com.estampaider.controller;

import com.estampaider.service.ProductoService;
import com.estampaider.model.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ProductoService productoService;

        @Test
        public void testObtenerProductos() throws Exception {
                // Crear datos de prueba
                List<Producto> productos = Arrays.asList(
                                new Producto(1L, "Camiseta de algodón", 40000.0, "Camiseta 100% algodón."),
                                new Producto(2L, "Mug 11 Oz", 19000.0, "Mug de cerámica personalizable."),
                                new Producto(3L, "Camiseta piel durazno", 30000.0, "Camiseta suave y cómoda."),
                                new Producto(4L, "Mug polka Travel 450 ml", 32000.0,
                                                "Mug térmico para llevar de viaje"),
                                new Producto(5L, "Llavero", 8000.0, "Llavero personalizado."),
                                new Producto(6L, "Imán", 7000.0, "Imán decorativo."),
                                new Producto(7L, "Cachucha", 20000.0, "Cachucha ajustable personalizada."),
                                new Producto(8L, "Caramañola", 25000.0, "Caramañola en aluminio 14 Oz."),
                                new Producto(9L, "Mug Mágico", 22000.0, "Pocillo que revela tu diseño"));

                // Simular la respuesta del servicio
                when(productoService.getAllProductos()).thenReturn(productos);

                // Ejecutar la petición y validar la respuesta
                mockMvc.perform(get("/api/productos"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(9))
                                .andExpect(jsonPath("$[0].nombre").value("Camiseta de algodón"))
                                .andExpect(jsonPath("$[1].nombre").value("Mug 11 Oz"));
        }

        @Test
        public void testObtenerProductoPorId() throws Exception {
                Producto producto = new Producto(1L, "Camiseta piel durazno", 30000.0, "Camiseta cómoda.");
                when(productoService.getProductoById(1L)).thenReturn(Optional.of(producto));

                mockMvc.perform(get("/api/productos/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nombre").value("Camiseta piel durazno"));
        }

        @Test
        public void testCrearProducto() throws Exception {
                Producto nuevoProducto = new Producto(10L, "Gorra Snapback", 25000.0, "Gorra ajustable personalizada.");
                when(productoService.saveProducto(any(Producto.class))).thenReturn(nuevoProducto);

                mockMvc.perform(post("/api/productos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                  "nombre": "Gorra Snapback",
                                                  "precio": 25000.0,
                                                  "descripcion": "Gorra ajustable personalizada."
                                                }
                                                """))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nombre").value("Gorra Snapback"));
        }

        @Test
        public void testEliminarProducto() throws Exception {
                when(productoService.deleteProducto(1L)).thenReturn(true);

                mockMvc.perform(delete("/api/productos/1"))
                                .andExpect(status().isNoContent());
        }
}