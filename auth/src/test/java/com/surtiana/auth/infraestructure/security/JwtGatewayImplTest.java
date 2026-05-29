package com.surtiana.auth.infraestructure.security;

import com.surtiana.auth.domain.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtGatewayImpl - Pruebas Unitarias")
class JwtGatewayImplTest {

    private JwtGatewayImpl jwtGateway;

    // IMPORTANTE: El secreto DEBE tener mínimo 32 caracteres para evitar errores de clave débil
    private final String secretoDePrueba = "esteEsUnSecretoSuperSeguroYMuyLargoParaElTest12345";
    private final long expiracionDePrueba = 3600000; // 1 hora en milisegundos

    @BeforeEach
    void setUp() {
        jwtGateway = new JwtGatewayImpl();

        // Simulamos la inyección de @Value en los campos privados
        ReflectionTestUtils.setField(jwtGateway, "secret", secretoDePrueba);
        ReflectionTestUtils.setField(jwtGateway, "expirationMs", expiracionDePrueba);
    }

    @Test
    @DisplayName("Debe generar un token JWT estructurado correctamente con los datos del usuario")
    void debeGenerarTokenValido() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setCedula("123456789");
        usuario.setNombre("Johan Surtiana");
        usuario.setCorreo("johan@gmail.com");
        usuario.setRol("ADMIN");

        // Act
        String tokenGenerado = jwtGateway.generarToken(usuario);

        // Assert
        assertNotNull(tokenGenerado, "El token no debería ser nulo");
        assertFalse(tokenGenerado.isEmpty(), "El token no debería estar vacío");

        // --- VERIFICACIÓN INTERNA DEL JWT ---
        // Volvemos a leer el token usando la misma clave para validar su contenido
        SecretKey key = Keys.hmacShaKeyFor(secretoDePrueba.getBytes(StandardCharsets.UTF_8));

        // Desencriptamos el payload (claims) del token generado
        Claims claims = Jwts.parser()
                .verifyWith(key) // Si usas una versión vieja de JJWT cambia a: .setSigningKey(key).build().parseClaimsJws(tokenGenerado).getBody()
                .build()
                .parseSignedClaims(tokenGenerado)
                .getPayload();

        // Evaluamos que la información grabada dentro del JWT sea exacta
        assertEquals(usuario.getCorreo(), claims.getSubject(), "El correo (Subject) no coincide");
        assertEquals(usuario.getCedula(), claims.get("cedula"), "La cédula no coincide");
        assertEquals(usuario.getNombre(), claims.get("nombre"), "El nombre no coincide");
        assertEquals(usuario.getRol(), claims.get("rol"), "El rol no coincide");

        // Validamos que tenga fecha de expiración y sea futura
        assertNotNull(claims.getExpiration(), "El token debería tener fecha de expiración");
        assertTrue(claims.getExpiration().after(new Date()), "El token ya nació expirado");
    }
}