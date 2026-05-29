package com.surtiana.auth.infraestructure.entry_points;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surtiana.auth.domain.model.Usuario;
import com.surtiana.auth.domain.usecase.UsuarioUseCase;
import com.surtiana.auth.infraestructure.driver_adapters.jpa_repository.UsuarioData;
import com.surtiana.auth.infraestructure.mapper.UsuarioMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UsuarioUseCase usuarioUseCase;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(usuarioController)
                .build();
    }

    @Test
    void deberiaGuardarUsuario() throws Exception {

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

        mockMvc.perform(post("/api/surtiana/usuario/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioData)))
                .andExpect(status().isOk());

        verify(usuarioMapper).toUsuario(any(UsuarioData.class));
        verify(usuarioUseCase).guardarUsuario(usuario);
    }

    @Test
    void deberiaBuscarUsuarioPorCedula() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setCedula("123");
        usuario.setCorreo("johan@gmail.com");

        when(usuarioUseCase.buscarUsuarioPorCc("123"))
                .thenReturn(usuario);

        mockMvc.perform(get("/api/surtiana/usuario/buscar/123"))
                .andExpect(status().isOk());

        verify(usuarioUseCase).buscarUsuarioPorCc("123");
    }

    @Test
    void deberiaEliminarUsuario() throws Exception {

        mockMvc.perform(delete("/api/surtiana/usuario/eliminar/123"))
                .andExpect(status().isNoContent());

        verify(usuarioUseCase).eliminarUsuarioPorCc("123");
    }

    @Test
    void deberiaHacerLogin() throws Exception {

        UsuarioData usuarioData = new UsuarioData();
        usuarioData.setCorreo("johan@gmail.com");
        usuarioData.setContrasena("123456");

        when(usuarioUseCase.login(
                usuarioData.getCorreo(),
                usuarioData.getContrasena()
        )).thenReturn("TOKEN_VALIDO");

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