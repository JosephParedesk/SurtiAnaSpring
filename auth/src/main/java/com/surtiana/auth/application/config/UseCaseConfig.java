package com.surtiana.auth.application.config;

import com.surtiana.auth.domain.model.gateway.EncrypterGateway;
import com.surtiana.auth.domain.model.gateway.JwtGateway;
import com.surtiana.auth.domain.model.gateway.UsuarioGateway;
import com.surtiana.auth.domain.usecase.UsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public UsuarioUseCase usuarioUseCase(UsuarioGateway usuarioGateway,
                                         EncrypterGateway encrypterGateway,
                                         JwtGateway jwtGateway) {
        return new UsuarioUseCase(usuarioGateway, encrypterGateway, jwtGateway);
    }

}
