package com.surtiana.catalogo.domain.usecase;

import com.surtiana.catalogo.domain.model.Producto;
import com.surtiana.catalogo.domain.model.gateway.ProductoGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor


public class ProductoUseCase {

    private final ProductoGateway ProductoGateway;

    public Producto guardarProducto(Producto producto){
        if(producto.getStock() == null){
            producto.setStock(0);
        }
        if(producto.getProductoId() == null || producto.getProductoId().isBlank()){
            throw new RuntimeException("El ID del producto es obligatorio");
        }

        if(producto.getPrecio() == null || producto.getPrecio() <= 0){
            throw new RuntimeException("El precio debe ser mayor a 0");
        }

        if( producto.getStock() < 0){
            throw new RuntimeException("El stock no puede ser negativo");
        }

        Producto productoGuardado = ProductoGateway.guardarProducto(producto);

        return productoGuardado;

    }

    public Producto buscarProductoPorId (String productoId){
        try{
            return ProductoGateway.buscarProductoPorId(productoId);

        }catch (Exception error){
            System.out.println(error.getMessage());
            Producto ProductoVacio = new Producto();
            return ProductoVacio;
        }
    }

    public void eliminarProductoPorId (String productoId){
        try{
            ProductoGateway.eliminarProductoPorId(productoId);
        }catch(Exception error){
            System.out.println(error.getMessage());
        }
    }

}
