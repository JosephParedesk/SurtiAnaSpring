package com.surtiana.auth.infraestructure.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("GlobalExceptionHandler - Pruebas Unitarias")
class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Configuramos MockMvc usando solo el controlador falso y nuestro manejador global
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Debe retornar 400 Bad Request cuando el JSON es inválido")
    void debeManejarHttpMessageNotReadableException() throws Exception {
        mockMvc.perform(get("/test/bad-json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Formato inválido en los datos enviados"));
    }

    @Test
    @DisplayName("Debe retornar 404 Not Found cuando no existe el elemento")
    void debeManejarNoSuchElementException() throws Exception {
        mockMvc.perform(get("/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Elemento no encontrado"));
    }

    @Test
    @DisplayName("Debe retornar 401 Unauthorized en caso de falla de seguridad")
    void debeManejarSecurityException() throws Exception {
        mockMvc.perform(get("/test/unauthorized"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("No tienes permisos de acceso"));
    }

    @Test
    @DisplayName("Debe retornar 400 Bad Request para cualquier otra RuntimeException")
    void debeManejarRuntimeException() throws Exception {
        mockMvc.perform(get("/test/runtime"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Ocurrió un error inesperado"));
    }


    @RestController
    static class TestController {

        @GetMapping("/test/bad-json")
        void throwBadJson() {
            throw new HttpMessageNotReadableException("Error leyendo JSON", (HttpInputMessage) null);
        }

        @GetMapping("/test/not-found")
        void throwNotFound() {
            throw new NoSuchElementException("Elemento no encontrado");
        }

        @GetMapping("/test/unauthorized")
        void throwUnauthorized() {
            throw new SecurityException("No tienes permisos de acceso");
        }

        @GetMapping("/test/runtime")
        void throwRuntime() {
            throw new RuntimeException("Ocurrió un error inesperado");
        }
    }
}