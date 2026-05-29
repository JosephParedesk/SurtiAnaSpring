package com.surtiana.auth.domain.model.gateway;

import com.ecommerce.auth.domain.model.Usuario;

public interface NotificationGateway {
    void enviarNotificacion(Usuario usuario);
    void enviarNotificacionRecuperacion(Usuario usuario);
}