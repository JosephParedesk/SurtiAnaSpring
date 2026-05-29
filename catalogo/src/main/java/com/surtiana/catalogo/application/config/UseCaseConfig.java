package com.surtiana.catalogo.application.config;

import com.surtiana.catalogo.domain.model.gateway.ProductoGateway;
import com.surtiana.catalogo.domain.usecase.ProductoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class UseCaseConfig {
    @Bean
    public ProductoUseCase productoUseCase(ProductoGateway productoGateway){
        return new ProductoUseCase(productoGateway);
    }

}
