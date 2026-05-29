package com.ecommerce.catalgo.domain.model.gateway;

import com.ecommerce.catalgo.domain.model.Venta;

import java.util.List;

public interface VentaGateway {
    Venta guardarVenta(Venta venta);
    Venta buscarVentaPorId(String ventaId);
    List<Venta> buscarVentasPorCliente(String clienteId);
}
