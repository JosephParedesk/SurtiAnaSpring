package com.ecommerce.catalgo.infrastructure.driver_adapters.jpa_repository.ventas;

import com.ecommerce.catalgo.domain.model.Venta;
import com.ecommerce.catalgo.domain.model.gateway.VentaGateway;
import com.ecommerce.catalgo.infrastructure.mapper.VentaMapper;
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
        return ventaDataJpaRepository.findByClienteId(clienteId)
                .stream()
                .map(ventaMapper::toVenta)
                .collect(Collectors.toList());
    }
}
