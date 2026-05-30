package com.surtiana.catalogo.infraestructure.mapper;


import com.surtiana.catalogo.domain.model.DetalleVenta;
import com.surtiana.catalogo.domain.model.Recibo;
import com.surtiana.catalogo.infraestructure.driver_adapters.jpa_repository.ventas.ReciboData;
import com.surtiana.catalogo.infraestructure.driver_adapters.jpa_repository.ventas.VentaDataJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReciboMapper {


    public ReciboData toReciboData(Recibo recibo) {
        ReciboData reciboData = new ReciboData();
        reciboData.setReciboId(recibo.getReciboId());
        reciboData.setVentaId(recibo.getVentaId());
        reciboData.setClienteId(recibo.getClienteId());
        reciboData.setFechaEmision(recibo.getFechaEmision());
        reciboData.setSubtotal(recibo.getSubtotal());
        reciboData.setImpuesto(recibo.getImpuesto());
        reciboData.setTotal(recibo.getTotal());
        return reciboData;
    }


    public Recibo toRecibo(ReciboData reciboData, List<DetalleVenta> detalles) {
        if (reciboData == null) return null;

        return new Recibo(
                reciboData.getReciboId(),
                reciboData.getVentaId(),
                reciboData.getClienteId(),
                reciboData.getFechaEmision(),
                detalles,
                reciboData.getSubtotal(),
                reciboData.getImpuesto(),
                reciboData.getTotal()
        );
    }
}
