package com.ecommerce.catalgo.infrastructure.driver_adapters.jpa_repository.ventas;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recibos")
@Data
public class ReciboData {

    @Id
    private String reciboId;
    private String ventaId;
    private String clienteId;
    private LocalDateTime fechaEmision;
    private Double subtotal;
    private Double impuesto;
    private Double total;
}
