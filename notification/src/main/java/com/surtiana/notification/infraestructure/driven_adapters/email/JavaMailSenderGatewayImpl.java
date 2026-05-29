package com.surtiana.notification.infraestructure.driven_adapters.email;

import com.surtiana.notification.domain.model.Notificacion;
import com.surtiana.notification.domain.model.gateway.EmailGateway;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Component
@RequiredArgsConstructor
public class JavaMailSenderGatewayImpl implements EmailGateway {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void enviarEmail(Notificacion notificacion) {
        try {
            log.info("Enviando correo a: {}", notificacion.getEmail());

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(notificacion.getEmail());
            helper.setSubject(resolverAsunto(notificacion.getTipo()));

            Context context = new Context();
            context.setVariable("tipo", notificacion.getTipo());
            context.setVariable("mensaje", notificacion.getMensaje());
            context.setVariable("email", notificacion.getEmail());

            String htmlContent = templateEngine.process("email/notificacion", context);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
            log.info("Correo enviado exitosamente a: {}", notificacion.getEmail());

        } catch (MessagingException e) {
            log.error("Error enviando correo a {}: {}", notificacion.getEmail(), e.getMessage());
            throw new RuntimeException("Error al enviar el correo de notificación", e);
        }
    }

    private String resolverAsunto(String tipo) {
        return switch (tipo) {
            case "Registro Usuario" -> "¡Bienvenido a SurtiAna! Tu cuenta ha sido creada";
            case "Recuperar Contraseña" -> "Solicitud de recuperación de contraseña";
            default -> "Notificación de SurtiAna - " + tipo;
        };
    }
}