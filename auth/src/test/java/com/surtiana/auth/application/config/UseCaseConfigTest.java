package com.surtiana.auth.application.config;

import com.surtiana.auth.domain.model.gateway.EncrypterGateway;
import com.surtiana.auth.domain.model.gateway.JwtGateway;
import com.surtiana.auth.domain.model.gateway.UsuarioGateway;
import com.surtiana.auth.domain.usecase.UsuarioUseCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class UseCaseConfigTest {

    @Test
    void usuarioUseCaseBean() {

        // Arrange
        UseCaseConfig config = new UseCaseConfig();

        UsuarioGateway usuarioGateway = mock(UsuarioGateway.class);
        EncrypterGateway encrypterGateway = mock(EncrypterGateway.class);
        JwtGateway jwtGateway = mock(JwtGateway.class);

        // Act
        UsuarioUseCase useCase = config.usuarioUseCase(
                usuarioGateway,
                encrypterGateway,
                jwtGateway
        );

        // Assert
        assertNotNull(useCase);
    }
}