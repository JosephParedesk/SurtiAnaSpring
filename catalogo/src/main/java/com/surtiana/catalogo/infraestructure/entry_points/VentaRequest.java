package com.surtiana.catalogo.infraestructure.entry_points;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaRequest {
    private String clienteId;
    private List<DetalleRequest> detalles;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetalleRequest {
        private String productoId;
        private Integer cantidad;
    }
}
