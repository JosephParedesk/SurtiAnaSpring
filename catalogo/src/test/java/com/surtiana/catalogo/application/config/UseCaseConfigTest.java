package com.surtiana.catalogo.application.config;

import com.surtiana.catalogo.domain.model.gateway.ProductoGateway;
import com.surtiana.catalogo.domain.model.gateway.ReciboGateway;
import com.surtiana.catalogo.domain.model.gateway.VentaGateway;
import com.surtiana.catalogo.domain.usecase.ProductoUseCase;
import com.surtiana.catalogo.domain.usecase.VentaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class UseCaseConfigTest {

    private UseCaseConfig useCaseConfig;

    @BeforeEach
    void setUp() {
        useCaseConfig = new UseCaseConfig();
    }

    @Test
    void productoUseCase_Exitoso_RetornaInstancia() {
        // Arrange
        ProductoGateway productoGateway = mock(ProductoGateway.class);

        // Act
        ProductoUseCase resultado = useCaseConfig.productoUseCase(productoGateway);

        // Assert
        assertNotNull(resultado);
    }

    @Test
    void ventaUseCase_Exitoso_RetornaInstancia() {
        // Arrange
        VentaGateway ventaGateway = mock(VentaGateway.class);
        ProductoGateway productoGateway = mock(ProductoGateway.class);
        ReciboGateway reciboGateway = mock(ReciboGateway.class);

        // Act
        VentaUseCase resultado = useCaseConfig.ventaUseCase(ventaGateway, productoGateway, reciboGateway);

        // Assert
        assertNotNull(resultado);
    }
}