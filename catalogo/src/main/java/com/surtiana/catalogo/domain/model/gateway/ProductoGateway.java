package com.surtiana.catalogo.domain.model.gateway;


import com.surtiana.catalogo.domain.model.Producto;

public interface ProductoGateway {
    Producto guardarProducto(Producto producto);

    Producto buscarProductoPorId (String productoId);

    void eliminarProductoPorId (String productoId);
}
