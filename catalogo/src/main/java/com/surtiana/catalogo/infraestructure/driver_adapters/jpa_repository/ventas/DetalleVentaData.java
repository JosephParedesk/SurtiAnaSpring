package com.surtiana.catalogo.infraestructure.driver_adapters.jpa_repository.ventas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalles_venta")
@Data
public class DetalleVentaData {

    @Id
    private String detalleId;
    private String productoId;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ventaId")
    private VentaData venta;
}
