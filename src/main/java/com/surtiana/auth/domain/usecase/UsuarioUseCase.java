package com.surtiana.auth.domain.usecase;


import com.surtiana.auth.domain.model.Usuario;
import com.surtiana.auth.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public Usuario guardarUsuario(Usuario usuario){

        if(usuario.getCedula() == null || usuario.getCedula().trim().isEmpty() ||
                usuario.getNombre() == null || usuario.getNombre().trim().isEmpty() ||
                usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty() ||
                usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()){

            throw new RuntimeException("Faltan campos obligatorios o están vacíos");
        }

        String passEncrypter = encrypterGateway.encrypt(usuario.getContrasena());
        usuario.setContrasena(passEncrypter);

        Usuario usuarioGuardado = usuarioGateway.guardarUsuario(usuario);

        return usuarioGuardado;
    }

    public Usuario buscarUsuarioPorCc(String cedula){

        Usuario usuario = usuarioGateway.buscarUsuarioPorCc(cedula);

        if(usuario == null){
            throw new NoSuchElementException("Usuario no encontrado");
        }

        return usuario;
    }

    public void eliminarUsuarioPorCc (String cedula){

        Usuario usuario = usuarioGateway.buscarUsuarioPorCc(cedula);

        if(usuario == null){
            throw new NoSuchElementException("Usuario no encontrado");
        }

        usuarioGateway.eliminarUsuarioPorCc(cedula);
    }

    public String login(String email, String password) {

        if (email == null || password == null) {
            throw new RuntimeException("Email y contraseña son obligatorios");
        }

        if (!email.contains("@")) {
            throw new RuntimeException("Correo inválido");
        }

        Usuario usuario = usuarioGateway.buscarPorCorreo(email);

        if (usuario == null || usuario.getCedula() == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (usuario.getContrasena() == null) {
            throw new RuntimeException("Error en datos del usuario");
        }

        if (!encrypterGateway.matches(password, usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return "Login exitoso";
    }


}
