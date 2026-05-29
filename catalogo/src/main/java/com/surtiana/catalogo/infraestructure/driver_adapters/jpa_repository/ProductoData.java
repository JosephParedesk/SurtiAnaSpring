package com.surtiana.catalogo.infraestructure.driver_adapters.jpa_repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productos")
@Data

public class ProductoData {

    @Id
    private String productoId;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
}
