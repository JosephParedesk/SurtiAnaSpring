package com.surtiana.auth.domain.model.gateway;

import com.surtiana.auth.domain.model.Usuario;

public interface NotificationGateway {
    void enviarNotificacion(Usuario usuario);
    void enviarNotificacionRecuperacion(Usuario usuario);
}