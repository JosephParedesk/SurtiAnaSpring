package com.surtiana.notification.infraestructure.entry_points;

import com.surtiana.notification.domain.model.gateway.EmailGateway;
import com.surtiana.notification.infraestructure.entry_points.dto.EventoNotificacionDTO;
import com.surtiana.notification.infraestructure.mapper.MapperNotificacion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificacionController {

    private final EmailGateway emailGateway;
    private final MapperNotificacion mapperNotificacion;

    @PostMapping("/send")
    public ResponseEntity<String> enviarNotificacion(@Valid @RequestBody EventoNotificacionDTO eventoDTO) {
        log.info("Evento recibido: tipo={}, email={}", eventoDTO.getTipo(), eventoDTO.getEmail());
        try {
            emailGateway.enviarEmail(mapperNotificacion.toNotificacion(eventoDTO));
            return ResponseEntity.ok("Notificación enviada exitosamente a " + eventoDTO.getEmail());
        } catch (Exception e) {
            log.error("Error procesando notificación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la notificación");
        }
    }
}