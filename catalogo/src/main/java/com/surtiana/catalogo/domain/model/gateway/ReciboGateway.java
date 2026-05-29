package com.surtiana.catalogo.domain.model.gateway;


import com.surtiana.catalogo.domain.model.Recibo;

public interface ReciboGateway {
    Recibo guardarRecibo(Recibo recibo);
    Recibo buscarReciboPorVentaId(String ventaId);
    Recibo buscarReciboPorId(String reciboId);
}
