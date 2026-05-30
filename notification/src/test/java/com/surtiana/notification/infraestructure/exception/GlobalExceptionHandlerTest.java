package com.surtiana.notification.infraestructure.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleMailException_RetornaBadGateway() {
        MailException exception = mock(MailException.class);

        ResponseEntity<String> resultado = globalExceptionHandler.handleMailException(exception);

        assertNotNull(resultado);
        assertEquals(HttpStatus.BAD_GATEWAY, resultado.getStatusCode());
        assertEquals("Error al enviar el correo", resultado.getBody());
    }
}