package com.surtiana.notification.domain.model.gateway;

import com.surtiana.notification.domain.model.Notificacion;

public interface EmailGateway {
    void enviarEmail(Notificacion notificacion);
}
