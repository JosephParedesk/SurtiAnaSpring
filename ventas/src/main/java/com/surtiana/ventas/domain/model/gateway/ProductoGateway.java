package com.surtiana.ventas.domain.model.gateway;

import com.surtiana.ventas.domain.model.Producto;

public interface ProductoGateway {
    Producto buscarProductoPorId(String productoId);
    Producto actualizarProducto(Producto producto);
}
