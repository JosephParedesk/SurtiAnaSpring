package com.surtiana.auth.infraestructure.notification;

import com.surtiana.auth.domain.model.Usuario;
import com.surtiana.auth.domain.model.gateway.NotificationGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationGatewayImpl implements NotificationGateway {

    private final RestTemplate restTemplate;

    @Value("${notification.service.url:http://localhost:9092/api/notification/send}")
    private String notificationServiceUrl;

    @Override
    public void enviarNotificacion(Usuario usuario) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = Map.of(
                    "tipo", "Registro Usuario",
                    "email", usuario.getCorreo(),
                    "numeroTelefono", usuario.getTelefono() != null ? usuario.getTelefono() : "",
                    "mensaje", "Hola " + usuario.getNombre() + ", tu cuenta ha sido creada exitosamente."
            );

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            restTemplate.postForObject(notificationServiceUrl, request, String.class);
            log.info("Notificación enviada a: {}", usuario.getCorreo());

        } catch (Exception e) {
            log.error("Error enviando notificación: {}", e.getMessage());
        }
    }

    @Override
    public void enviarNotificacionRecuperacion(Usuario usuario) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = Map.of(
                    "tipo", "Recuperación Contraseña",
                    "email", usuario.getCorreo(),
                    "numeroTelefono", usuario.getTelefono() != null ? usuario.getTelefono() : "",
                    "mensaje", "Hola " + usuario.getNombre() + ", usa el siguiente token para restablecer tu contraseña: "
                            + usuario.getResetPasswordToken() + ". Expira en 30 minutos."
            );

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            restTemplate.postForObject(notificationServiceUrl, request, String.class);
            log.info("Notificación de recuperación enviada a: {}", usuario.getCorreo());

        } catch (Exception e) {
            log.error("Error enviando notificación de recuperación a {}", usuario.getCorreo(), e);
            throw new RuntimeException("No fue posible enviar el correo de recuperación");
        }
    }
}
