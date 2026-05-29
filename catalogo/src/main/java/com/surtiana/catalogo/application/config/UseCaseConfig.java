package com.surtiana.catalogo.application.config;

import com.surtiana.catalogo.domain.model.gateway.ProductoGateway;
import com.surtiana.catalogo.domain.model.gateway.ReciboGateway;
import com.surtiana.catalogo.domain.model.gateway.VentaGateway;
import com.surtiana.catalogo.domain.usecase.ProductoUseCase;
import com.surtiana.catalogo.domain.usecase.VentaUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class UseCaseConfig {
    @Bean
    public ProductoUseCase productoUseCase(ProductoGateway productoGateway){
        return new ProductoUseCase(productoGateway);
    }
    @Bean
    public VentaUseCase ventaUseCase(VentaGateway ventaGateway, ProductoGateway productoGateway, ReciboGateway reciboGateway) {
        return new VentaUseCase(ventaGateway, productoGateway, reciboGateway);
    }

}
