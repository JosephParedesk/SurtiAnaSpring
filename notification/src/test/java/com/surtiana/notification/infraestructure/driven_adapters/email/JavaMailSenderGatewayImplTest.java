package com.surtiana.notification.infraestructure.driven_adapters.email;

import com.surtiana.notification.domain.model.Notificacion;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JavaMailSenderGatewayImplTest {

    private JavaMailSenderGatewayImpl javaMailSenderGateway;
    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;

    @BeforeEach
    void setUp() {
        javaMailSender = mock(JavaMailSender.class);
        templateEngine = mock(TemplateEngine.class);
        javaMailSenderGateway = new JavaMailSenderGatewayImpl(javaMailSender, templateEngine);
        ReflectionTestUtils.setField(javaMailSenderGateway, "from", "remitente@surtiana.com");
    }

    @Test
    void enviarEmail_RegistroUsuario_Exitoso() {
        Notificacion notificacion = mock(Notificacion.class);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(notificacion.getEmail()).thenReturn("cliente@test.com");
        when(notificacion.getTipo()).thenReturn("Registro Usuario");
        when(notificacion.getMensaje()).thenReturn("Bienvenido a la plataforma");
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("email/notificacion"), any(Context.class))).thenReturn("<h1>Html</h1>");

        javaMailSenderGateway.enviarEmail(notificacion);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    void enviarEmail_RecuperarContrasena_Exitoso() {
        Notificacion notificacion = mock(Notificacion.class);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(notificacion.getEmail()).thenReturn("cliente@test.com");
        when(notificacion.getTipo()).thenReturn("Recuperar Contraseña");
        when(notificacion.getMensaje()).thenReturn("Codigo de recuperacion");
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("email/notificacion"), any(Context.class))).thenReturn("<h1>Html</h1>");

        javaMailSenderGateway.enviarEmail(notificacion);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    void enviarEmail_TipoPorDefecto_Exitoso() {
        Notificacion notificacion = mock(Notificacion.class);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(notificacion.getEmail()).thenReturn("cliente@test.com");
        when(notificacion.getTipo()).thenReturn("Actualizacion Datos");
        when(notificacion.getMensaje()).thenReturn("Tus datos fueron actualizados");
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("email/notificacion"), any(Context.class))).thenReturn("<h1>Html</h1>");

        javaMailSenderGateway.enviarEmail(notificacion);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    void enviarEmail_MessagingException_LanzaRuntimeException() throws MessagingException {
        Notificacion notificacion = mock(Notificacion.class);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(notificacion.getEmail()).thenReturn("cliente@test.com");
        when(notificacion.getTipo()).thenReturn("Registro Usuario");
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(new MessagingException("Error de conexion smtp")).when(mimeMessage).setFrom(any(jakarta.mail.Address.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            javaMailSenderGateway.enviarEmail(notificacion);
        });

        assertEquals("Error al enviar el correo de notificación", exception.getMessage());
    }
}