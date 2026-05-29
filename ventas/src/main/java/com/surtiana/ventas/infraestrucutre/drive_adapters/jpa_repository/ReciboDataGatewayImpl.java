package com.ecommerce.catalgo.infrastructure.driver_adapters.jpa_repository.ventas;

import com.ecommerce.catalgo.domain.model.Recibo;
import com.ecommerce.catalgo.domain.model.gateway.ReciboGateway;
import com.ecommerce.catalgo.infrastructure.mapper.ReciboMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReciboDataGatewayImpl implements ReciboGateway {

    private final ReciboDataJpaRepository reciboDataJpaRepository;
    private final ReciboMapper reciboMapper;

    @Override
    public Recibo guardarRecibo(Recibo recibo) {
        ReciboData reciboData = reciboMapper.toReciboData(recibo);
        return reciboMapper.toRecibo(reciboDataJpaRepository.save(reciboData));
    }

    @Override
    public Recibo buscarReciboPorVentaId(String ventaId) {
        return reciboMapper.toRecibo(reciboDataJpaRepository.findByVentaId(ventaId).orElse(null));
    }

    @Override
    public Recibo buscarReciboPorId(String reciboId) {
        return reciboMapper.toRecibo(reciboDataJpaRepository.findById(reciboId).orElse(null));
    }
}
