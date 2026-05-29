package com.ecommerce.catalgo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venta {
    private String ventaId;
    private String clienteId;
    private LocalDateTime fecha;
    private List<DetalleVenta> detalles;
    private Double total;
    private String estado; // COMPLETADA, CANCELADA
}
