package com.surtiana.auth.domain.model.gateway;

import com.surtiana.auth.domain.model.Usuario;

public interface JwtGateway {
    String generarToken(Usuario usuario);
}
