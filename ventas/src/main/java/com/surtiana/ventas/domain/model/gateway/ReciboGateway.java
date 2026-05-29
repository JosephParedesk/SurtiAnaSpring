package com.ecommerce.catalgo.domain.model.gateway;

import com.ecommerce.catalgo.domain.model.Recibo;

public interface ReciboGateway {
    Recibo guardarRecibo(Recibo recibo);
    Recibo buscarReciboPorVentaId(String ventaId);
    Recibo buscarReciboPorId(String reciboId);
}
