package com.surtiana.notification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {
    private String tipo;
    private String email;
    private String numeroTelefono;
    private String mensaje;
}