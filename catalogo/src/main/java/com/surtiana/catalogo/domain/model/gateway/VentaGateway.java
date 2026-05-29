package com.surtiana.catalogo.domain.model.gateway;


import com.surtiana.catalogo.domain.model.Venta;

import java.util.List;

public interface VentaGateway {
    Venta guardarVenta(Venta venta);
    Venta buscarVentaPorId(String ventaId);
    List<Venta> buscarVentasPorCliente(String clienteId);
}
