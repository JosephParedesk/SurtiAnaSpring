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
    public ResponseEntity<String> enviarNotificacion(
            @Valid @RequestBody EventoNotificacionDTO eventoDTO) {

        emailGateway.enviarEmail(mapperNotificacion.toNotificacion(eventoDTO));

        return ResponseEntity.ok("Notificación enviada exitosamente a " + eventoDTO.getEmail());
    }
}