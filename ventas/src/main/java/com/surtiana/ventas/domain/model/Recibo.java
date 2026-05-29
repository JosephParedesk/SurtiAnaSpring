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
public class Recibo {
    private String reciboId;
    private String ventaId;
    private String clienteId;
    private LocalDateTime fechaEmision;
    private List<DetalleVenta> detalles;
    private Double subtotal;
    private Double impuesto; // 19% IVA
    private Double total;
}
