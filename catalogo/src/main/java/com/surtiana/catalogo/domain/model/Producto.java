package com.surtiana.catalogo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Producto {
    private String productoId;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
}
