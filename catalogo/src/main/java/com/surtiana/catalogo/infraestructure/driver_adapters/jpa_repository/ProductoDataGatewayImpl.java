package com.surtiana.catalogo.infraestructure.driver_adapters.jpa_repository;


import com.surtiana.catalogo.domain.model.Producto;
import com.surtiana.catalogo.domain.model.gateway.ProductoGateway;
import com.surtiana.catalogo.infraestructure.mapper.ProductoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductoDataGatewayImpl implements ProductoGateway {

    private final ProductoDataJpaRepository productoDataJpaRepository;
    private final ProductoMapper productoMapper;

    @Override
    public Producto guardarProducto(Producto producto) {
        ProductoData productoDataGuardar = productoMapper.topruductoData(producto);

        return productoMapper.topruducto(productoDataJpaRepository.save(productoDataGuardar));
    }

    @Override
    public Producto buscarProductoPorId(String productoId) {
        return productoMapper.topruducto(productoDataJpaRepository.findById(productoId).orElse(null));
    }


    @Override
    public void eliminarProductoPorId(String productoId) {
        productoDataJpaRepository.deleteById(productoId);
    }
}
