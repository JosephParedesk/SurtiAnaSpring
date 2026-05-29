package com.surtiana.notification.infraestructure.entry_points.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoNotificacionDTO {

    @NotBlank(message = "El tipo de notificación es obligatorio")
    private String tipo;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    private String numeroTelefono;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;
}