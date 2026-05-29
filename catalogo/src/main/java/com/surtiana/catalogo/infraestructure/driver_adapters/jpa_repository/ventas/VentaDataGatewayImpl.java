package com.surtiana.catalogo.infraestructure.driver_adapters.jpa_repository.ventas;


import com.surtiana.catalogo.domain.model.Venta;
import com.surtiana.catalogo.domain.model.gateway.VentaGateway;
import com.surtiana.catalogo.infraestructure.mapper.VentaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class VentaDataGatewayImpl implements VentaGateway {

    private final VentaDataJpaRepository ventaDataJpaRepository;
    private final VentaMapper ventaMapper;

    @Override
    public Venta guardarVenta(Venta venta) {
        VentaData ventaData = ventaMapper.toVentaData(venta);
        return ventaMapper.toVenta(ventaDataJpaRepository.save(ventaData));
    }

    @Override
    public Venta buscarVentaPorId(String ventaId) {
        return ventaMapper.toVenta(ventaDataJpaRepository.findById(ventaId).orElse(null));
    }

    @Override
    public List<Venta> buscarVentasPorCliente(String clienteId) {
        return ventaDataJpaRepository.findByClienteId(clienteId).stream().map(ventaMapper::toVenta).collect(Collectors.toList());
    }
}
