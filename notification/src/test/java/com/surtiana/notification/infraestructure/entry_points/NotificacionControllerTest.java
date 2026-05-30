package com.surtiana.notification.infraestructure.entry_points;

import com.surtiana.notification.domain.model.Notificacion;
import com.surtiana.notification.domain.model.gateway.EmailGateway;
import com.surtiana.notification.infraestructure.entry_points.dto.EventoNotificacionDTO;
import com.surtiana.notification.infraestructure.mapper.MapperNotificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificacionControllerTest {

    private NotificacionController notificacionController;
    private EmailGateway emailGateway;
    private MapperNotificacion mapperNotificacion;

    @BeforeEach
    void setUp() {
        emailGateway = mock(EmailGateway.class);
        mapperNotificacion = mock(MapperNotificacion.class);
        notificacionController = new NotificacionController(emailGateway, mapperNotificacion);
    }

    @Test
    void enviarNotificacion_Exitoso_RetornaOk() {
        EventoNotificacionDTO eventoDTO = mock(EventoNotificacionDTO.class);
        Notificacion notificacion = mock(Notificacion.class);
        when(eventoDTO.getEmail()).thenReturn("usuario@surtiana.com");
        when(mapperNotificacion.toNotificacion(eventoDTO)).thenReturn(notificacion);

        ResponseEntity<String> resultado = notificacionController.enviarNotificacion(eventoDTO);

        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals("Notificación enviada exitosamente a usuario@surtiana.com", resultado.getBody());
        verify(emailGateway, times(1)).enviarEmail(notificacion);
    }
}