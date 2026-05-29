package com.surtiana.auth.infraestructure.entry_points;

import org.springframework.boot.test.json.JacksonTester;
import com.surtiana.auth.domain.model.Usuario;
import com.surtiana.auth.domain.usecase.UsuarioUseCase;
import com.surtiana.auth.infraestructure.driver_adapters.jpa_repository.UsuarioData;
import com.surtiana.auth.infraestructure.mapper.UsuarioMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioUseCase usuarioUseCase;

    @MockBean
    private UsuarioMapper usuarioMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deberiaGuardarUsuario() throws Exception {

        // Arrange
        UsuarioData usuarioData = new UsuarioData();
        usuarioData.setCedula("123");
        usuarioData.setCorreo("johan@gmail.com");
        usuarioData.setContrasena("123456");

        Usuario usuario = new Usuario();
        usuario.setCedula("123");
        usuario.setCorreo("johan@gmail.com");

        when(usuarioMapper.toUsuario(usuarioData))
                .thenReturn(usuario);

        when(usuarioUseCase.guardarUsuario(usuario))
                .thenReturn(usuario);

        // Act & Assert
        mockMvc.perform(post("/api/surtiana/usuario/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioData)))
                .andExpect(status().isOk());

        verify(usuarioMapper).toUsuario(any(UsuarioData.class));
        verify(usuarioUseCase).guardarUsuario(usuario);
    }

    @Test
    void deberiaBuscarUsuarioPorCedula() throws Exception {

        // Arrange
        Usuario usuario = new Usuario();
        usuario.setCedula("123");
        usuario.setCorreo("johan@gmail.com");

        when(usuarioUseCase.buscarUsuarioPorCc("123"))
                .thenReturn(usuario);

        // Act & Assert
        mockMvc.perform(get("/api/surtiana/usuario/buscar/123"))
                .andExpect(status().isOk());

        verify(usuarioUseCase).buscarUsuarioPorCc("123");
    }

    @Test
    void deberiaEliminarUsuario() throws Exception {

        // Act & Assert
        mockMvc.perform(delete("/api/surtiana/usuario/eliminar/123"))
                .andExpect(status().isNoContent());

        verify(usuarioUseCase).eliminarUsuarioPorCc("123");
    }

    @Test
    void deberiaHacerLogin() throws Exception {

        // Arrange
        UsuarioData usuarioData = new UsuarioData();
        usuarioData.setCorreo("johan@gmail.com");
        usuarioData.setContrasena("123456");

        when(usuarioUseCase.login(
                usuarioData.getCorreo(),
                usuarioData.getContrasena()
        )).thenReturn("TOKEN_VALIDO");

        // Act & Assert
        mockMvc.perform(post("/api/surtiana/usuario/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioData)))
                .andExpect(status().isOk())
                .andExpect(content().string("TOKEN_VALIDO"));

        verify(usuarioUseCase).login(
                usuarioData.getCorreo(),
                usuarioData.getContrasena()
        );
    }
}