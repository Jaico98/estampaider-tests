package com.estampaider.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "pedido_productos", joinColumns = @JoinColumn(name = "pedido_id"), inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productos;

    private int cantidad;
    private String clienteNombre;
    private String clienteCorreo;

    public Pedido() {
    }

    public Pedido(List<Producto> productos, int cantidad, String clienteNombre, String clienteCorreo) {
        this.productos = productos;
        this.cantidad = cantidad;
        this.clienteNombre = clienteNombre;
        this.clienteCorreo = clienteCorreo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteCorreo() {
        return clienteCorreo;
    }

    public void setClienteCorreo(String clienteCorreo) {
        this.clienteCorreo = clienteCorreo;
    }
}
