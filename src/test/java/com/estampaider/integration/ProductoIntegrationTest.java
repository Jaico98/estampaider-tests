package com.estampaider.integration;

import com.estampaider.model.Producto;
import com.estampaider.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
public class ProductoIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProductoRepository productoRepository;

    private Producto producto;

    @BeforeEach
    void setUp() {
        productoRepository.deleteAll(); // Eliminado .block()

        // Se ajustó el constructor con los 4 parámetros requeridos
        producto = new Producto(null, "Camiseta Estampada", 40000.0, "Camiseta de algodón con estampado único");
        productoRepository.save(producto); // Guardar el producto en la BD antes de probar
    }

    @Test
    void testCrearProducto() {
        Producto nuevoProducto = new Producto(null, "Mug Estampado", 19000.0, "Mug de cerámica 11oz");

        webTestClient.post()
                .uri("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(nuevoProducto) // Usamos bodyValue en lugar de Mono.just()
                .exchange()
                .expectStatus().isOk()
                .expectBody(Producto.class)
                .value(response -> assertThat(response.getNombre()).isEqualTo("Mug Estampado"));
    }

    @Test
    void testObtenerProductoPorId() {
        Producto nuevoProducto = productoRepository.save(producto); // Eliminado .block()

        webTestClient.get()
                .uri("/api/productos/{id}", nuevoProducto.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Producto.class)
                .value(response -> assertThat(response.getNombre()).isEqualTo("Camiseta Estampada"));
    }

    @Test
    void testEliminarProducto() {
        Producto nuevoProducto = productoRepository.save(producto); // Eliminado .block()

        webTestClient.delete()
                .uri("/api/productos/{id}", nuevoProducto.getId())
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get()
                .uri("/api/productos/{id}", nuevoProducto.getId())
                .exchange()
                .expectStatus().isNotFound();

    }
}
