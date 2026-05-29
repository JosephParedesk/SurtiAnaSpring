package com.surtiana.catalogo.infraestructure.driver_adapters.jpa_repository.ventas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ventas")
@Data
public class VentaData {

    @Id
    private String ventaId;
    private String clienteId;
    private LocalDateTime fecha;
    private Double total;
    private String estado;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetalleVentaData> detalles;
}
