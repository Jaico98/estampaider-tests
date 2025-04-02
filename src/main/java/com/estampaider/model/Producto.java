package com.estampaider.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.AllArgsConstructor;

/**
 * Entidad que representa un producto en la base de datos.
 */

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Producto {

    /** Identificador único del producto. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del producto. */
    private String nombre;

    /** Precio del producto. */
    private double precio;

    /** Descripción del producto. */
    private String descripcion;
}
