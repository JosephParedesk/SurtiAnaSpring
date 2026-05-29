package com.surtiana.auth.infraestructure.mapper;

import com.surtiana.auth.domain.model.Usuario;
import com.surtiana.auth.infraestructure.driver_adapters.jpa_repository.UsuarioData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("UsuarioMapper - Pruebas Unitarias")
class UsuarioMapperTest {

    private UsuarioMapper usuarioMapper;

    @BeforeEach
    void setUp() {
        // Al ser una clase simple, la instanciamos directamente sin mocks
        usuarioMapper = new UsuarioMapper();
    }

    @Test
    @DisplayName("Debe mapear correctamente un objeto de Dominio a la Entidad de Base de Datos")
    void debeMapearAUsuarioData() {
        // Arrange
        Usuario usuario = new Usuario(
                "123456789",
                "Johan Surtiana",
                "johan@gmail.com",
                "password-seguro",
                "3101234567",
                "ADMIN"
        );

        // Act
        UsuarioData resultado = usuarioMapper.tousuarioData(usuario);

        // Assert
        assertNotNull(resultado, "El objeto mapeado no debería ser nulo");
        assertEquals(usuario.getCedula(), resultado.getCedula());
        assertEquals(usuario.getNombre(), resultado.getNombre());
        assertEquals(usuario.getCorreo(), resultado.getCorreo());
        assertEquals(usuario.getContrasena(), resultado.getContrasena());
        assertEquals(usuario.getTelefono(), resultado.getTelefono());
        assertEquals(usuario.getRol(), resultado.getRol());
    }

    @Test
    @DisplayName("Debe mapear correctamente un objeto de Base de Datos al Modelo de Dominio")
    void debeMapearAUsuario() {
        // Arrange
        UsuarioData usuarioData = new UsuarioData(
                "987654321",
                "Carlos Perez",
                "carlos@gmail.com",
                "password-encriptado",
                "3207654321",
                "USER"
        );

        // Act
        Usuario resultado = usuarioMapper.toUsuario(usuarioData);

        // Assert
        assertNotNull(resultado, "El objeto mapeado no debería ser nulo");
        assertEquals(usuarioData.getCedula(), resultado.getCedula());
        assertEquals(usuarioData.getNombre(), resultado.getNombre());
        assertEquals(usuarioData.getCorreo(), resultado.getCorreo());
        assertEquals(usuarioData.getContrasena(), resultado.getContrasena());
        assertEquals(usuarioData.getTelefono(), resultado.getTelefono());
        assertEquals(usuarioData.getRol(), resultado.getRol());
    }
}