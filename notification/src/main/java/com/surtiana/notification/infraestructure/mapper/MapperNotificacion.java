package com.surtiana.notification.infraestructure.mapper;

import com.surtiana.notification.domain.model.Notificacion;
import com.surtiana.notification.infraestructure.entry_points.dto.EventoNotificacionDTO;
import org.springframework.stereotype.Component;

@Component
public class MapperNotificacion {

    public Notificacion toNotificacion(EventoNotificacionDTO dto) {
        return Notificacion.builder()
                .tipo(dto.getTipo())
                .email(dto.getEmail())
                .numeroTelefono(dto.getNumeroTelefono())
                .mensaje(dto.getMensaje())
                .build();
    }
}