package com.estampaider.controller;

import com.estampaider.model.Pedido;
import com.estampaider.repositories.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Obtener todos los pedidos
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    // Obtener un pedido por ID
    @GetMapping("/{id}")
    public Optional<Pedido> getPedidoById(@PathVariable Long id) {
        return pedidoRepository.findById(id);
    }

    // Crear un nuevo pedido
    @PostMapping
    public Pedido createPedido(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Eliminar un pedido por ID
    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable Long id) {
        pedidoRepository.deleteById(id);
    }
}
