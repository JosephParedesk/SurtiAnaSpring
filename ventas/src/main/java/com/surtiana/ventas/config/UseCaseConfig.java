package com.ecommerce.catalgo.application.config;

import com.ecommerce.catalgo.domain.model.gateway.ProductoGateway;
import com.ecommerce.catalgo.domain.model.gateway.ReciboGateway;
import com.ecommerce.catalgo.domain.model.gateway.VentaGateway;
import com.ecommerce.catalgo.domain.usecase.ProductoUseCase;
import com.ecommerce.catalgo.domain.usecase.VentaUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public ProductoUseCase productoUseCase(ProductoGateway productoGateway) {
        return new ProductoUseCase(productoGateway);
    }

    @Bean
    public VentaUseCase ventaUseCase(VentaGateway ventaGateway,
                                     ProductoGateway productoGateway,
                                     ReciboGateway reciboGateway) {
        return new VentaUseCase(ventaGateway, productoGateway, reciboGateway);
    }
}
