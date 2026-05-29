package com.surtiana.auth.infraestructure.driver_adapters.jpa_repository;

import com.surtiana.auth.domain.model.Usuario;
import com.surtiana.auth.infraestructure.mapper.UsuarioMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioDataGatewayImplTest {

    @Mock
    private UsuarioDataJpaRepository usuarioDataJpaRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioDataGatewayImpl usuarioDataGateway;

    @Test
    void deberiaGuardarUsuario() {

        // Arrange
        Usuario usuario = new Usuario();
        UsuarioData usuarioData = new UsuarioData();

        when(usuarioMapper.tousuarioData(usuario))
                .thenReturn(usuarioData);

        when(usuarioDataJpaRepository.save(usuarioData))
                .thenReturn(usuarioData);

        when(usuarioMapper.toUsuario(usuarioData))
                .thenReturn(usuario);

        // Act
        Usuario resultado = usuarioDataGateway.guardarUsuario(usuario);

        // Assert
        assertNotNull(resultado);

        verify(usuarioMapper).tousuarioData(usuario);
        verify(usuarioDataJpaRepository).save(usuarioData);
        verify(usuarioMapper).toUsuario(usuarioData);
    }

    @Test
    void deberiaBuscarUsuarioPorCc() {

        // Arrange
        String cedula = "123";

        UsuarioData usuarioData = new UsuarioData();
        Usuario usuario = new Usuario();

        when(usuarioDataJpaRepository.findById(cedula))
                .thenReturn(Optional.of(usuarioData));

        when(usuarioMapper.toUsuario(usuarioData))
                .thenReturn(usuario);

        // Act
        Usuario resultado = usuarioDataGateway.buscarUsuarioPorCc(cedula);

        // Assert
        assertNotNull(resultado);

        verify(usuarioDataJpaRepository).findById(cedula);
        verify(usuarioMapper).toUsuario(usuarioData);
    }

    @Test
    void deberiaRetornarNullCuandoNoExisteCedula() {

        // Arrange
        String cedula = "999";

        when(usuarioDataJpaRepository.findById(cedula))
                .thenReturn(Optional.empty());

        // Act
        Usuario resultado = usuarioDataGateway.buscarUsuarioPorCc(cedula);

        // Assert
        assertNull(resultado);

        verify(usuarioDataJpaRepository).findById(cedula);
    }

    @Test
    void deberiaEliminarUsuarioPorCc() {

        // Arrange
        String cedula = "123";

        // Act
        usuarioDataGateway.eliminarUsuarioPorCc(cedula);

        // Assert
        verify(usuarioDataJpaRepository).deleteById(cedula);
    }

    @Test
    void deberiaBuscarPorCorreo() {

        // Arrange
        String correo = "johan@gmail.com";

        UsuarioData usuarioData = new UsuarioData();
        Usuario usuario = new Usuario();

        when(usuarioDataJpaRepository.findByCorreo(correo))
                .thenReturn(Optional.of(usuarioData));

        when(usuarioMapper.toUsuario(usuarioData))
                .thenReturn(usuario);

        // Act
        Usuario resultado = usuarioDataGateway.buscarPorCorreo(correo);

        // Assert
        assertNotNull(resultado);

        verify(usuarioDataJpaRepository).findByCorreo(correo);
        verify(usuarioMapper).toUsuario(usuarioData);
    }

    @Test
    void deberiaRetornarNullCuandoCorreoNoExiste() {

        // Arrange
        String correo = "noexiste@gmail.com";

        when(usuarioDataJpaRepository.findByCorreo(correo))
                .thenReturn(Optional.empty());

        // Act
        Usuario resultado = usuarioDataGateway.buscarPorCorreo(correo);

        // Assert
        assertNull(resultado);

        verify(usuarioDataJpaRepository).findByCorreo(correo);
    }
}