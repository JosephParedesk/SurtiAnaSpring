package com.surtiana.catalogo.infraestructure.driver_adapters.jpa_repository.ventas;


import com.surtiana.catalogo.domain.model.DetalleVenta;
import com.surtiana.catalogo.domain.model.Recibo;
import com.surtiana.catalogo.domain.model.Venta;
import com.surtiana.catalogo.domain.model.gateway.ReciboGateway;
import com.surtiana.catalogo.infraestructure.mapper.ReciboMapper;
import com.surtiana.catalogo.infraestructure.mapper.VentaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReciboDataGatewayImpl implements ReciboGateway {

    private final ReciboDataJpaRepository reciboDataJpaRepository;
    private final VentaDataJpaRepository ventaDataJpaRepository;
    private final ReciboMapper reciboMapper;
    private final VentaMapper ventaMapper;

    @Override
    public Recibo guardarRecibo(Recibo recibo) {
        ReciboData saved = reciboDataJpaRepository.save(reciboMapper.toReciboData(recibo));
        List<DetalleVenta> detalles = obtenerDetalles(saved.getVentaId());
        return reciboMapper.toRecibo(saved, detalles);
    }

    @Override
    public Recibo buscarReciboPorVentaId(String ventaId) {
        return reciboDataJpaRepository.findByVentaId(ventaId)
                .map(r -> reciboMapper.toRecibo(r, obtenerDetalles(r.getVentaId())))
                .orElse(null);
    }

    @Override
    public Recibo buscarReciboPorId(String reciboId) {
        return reciboDataJpaRepository.findById(reciboId)
                .map(r -> reciboMapper.toRecibo(r, obtenerDetalles(r.getVentaId())))
                .orElse(null);
    }

    private List<DetalleVenta> obtenerDetalles(String ventaId) {
        return ventaDataJpaRepository.findById(ventaId)
                .map(ventaMapper::toVenta)
                .map(Venta::getDetalles)
                .orElse(null);
    }
}
