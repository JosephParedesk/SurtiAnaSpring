package com.surtiana.catalogo.application.config;

import com.surtiana.catalogo.domain.model.gateway.ProductoGateway;
import com.surtiana.catalogo.domain.usecase.ProductoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UseCaseConfigTest {

    private UseCaseConfig useCaseConfig;
    private ProductoGateway productoGateway;

    @BeforeEach
    void setUp() {
        useCaseConfig = new UseCaseConfig();
        productoGateway = mock(ProductoGateway.class);
    }

    @Test
    void productoUseCase_ShouldReturnInstance() {
        // Arrange (Configurado en el setUp)

        // Act
        ProductoUseCase resultado = useCaseConfig.productoUseCase(productoGateway);

        // Assert
        assertNotNull(resultado);
    }
}