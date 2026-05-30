package com.surtiana.notification.infraestructure.mapper;

import com.surtiana.notification.domain.model.Notificacion;
import com.surtiana.notification.infraestructure.entry_points.dto.EventoNotificacionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MapperNotificacionTest {

    private MapperNotificacion mapperNotificacion;

    @BeforeEach
    void setUp() {
        mapperNotificacion = new MapperNotificacion();
    }

    @Test
    void toNotificacion_Exitoso_RetornaNotificacion() {
        EventoNotificacionDTO dto = mock(EventoNotificacionDTO.class);
        when(dto.getTipo()).thenReturn("Registro Usuario");
        when(dto.getEmail()).thenReturn("test@surtiana.com");
        when(dto.getNumeroTelefono()).thenReturn("1234567890");
        when(dto.getMensaje()).thenReturn("Bienvenido a SurtiAna");

        Notificacion resultado = mapperNotificacion.toNotificacion(dto);

        assertNotNull(resultado);
        assertEquals("Registro Usuario", resultado.getTipo());
        assertEquals("test@surtiana.com", resultado.getEmail());
        assertEquals("1234567890", resultado.getNumeroTelefono());
        assertEquals("Bienvenido a SurtiAna", resultado.getMensaje());
    }
}