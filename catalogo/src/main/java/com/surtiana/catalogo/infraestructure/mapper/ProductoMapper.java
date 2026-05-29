package com.surtiana.catalogo.infraestructure.mapper;

import com.surtiana.catalogo.domain.model.Producto;
import com.surtiana.catalogo.infraestructure.driver_adapters.jpa_repository.ProductoData;
import org.springframework.stereotype.Component;

@Component

public class ProductoMapper {

    public ProductoData topruductoData (Producto producto){
        return new ProductoData(
                producto.getProductoId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock()
        );
    }

    public Producto topruducto (ProductoData productoData){
        return new Producto(
                productoData.getProductoId(),
                productoData.getNombre(),
                productoData.getDescripcion(),
                productoData.getPrecio(),
                productoData.getStock()
        );
    }
}
