package com.surtiana.auth.domain.usecase;


import com.surtiana.auth.domain.model.Usuario;
import com.surtiana.auth.domain.model.gateway.EncrypterGateway;
import com.surtiana.auth.domain.model.gateway.JwtGateway;
import com.surtiana.auth.domain.model.gateway.NotificationGateway;
import com.surtiana.auth.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioGateway usuarioGateway;
    private final EncrypterGateway encrypterGateway;
    private final JwtGateway jwtGateway;
    private final NotificationGateway notificationGateway;


    public Usuario guardarUsuario(Usuario usuario){

        if(usuario.getCedula() == null || usuario.getCedula().trim().isEmpty() ||
                usuario.getNombre() == null || usuario.getNombre().trim().isEmpty() ||
                usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty() ||
                usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()){

            throw new RuntimeException("Faltan campos obligatorios o están vacíos");
        }
        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {
            usuario.setRol("user");
        }
        String passEncrypter = encrypterGateway.encrypt(usuario.getContrasena());
        usuario.setContrasena(passEncrypter);

        Usuario usuarioGuardado = usuarioGateway.guardarUsuario(usuario);
        notificationGateway.enviarNotificacion(usuarioGuardado);

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

        return jwtGateway.generarToken(usuario);
    }
    public void forgotPassword(String email) {

        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("El correo es obligatorio");
        }

        Usuario usuario = usuarioGateway.buscarPorCorreo(email);

        if (usuario == null) {
            throw new NoSuchElementException("No existe una cuenta con ese correo");
        }

        String token = UUID.randomUUID().toString();
        usuario.setResetPasswordToken(token);
        usuario.setResetPasswordTokenExpiry(LocalDateTime.now().plusMinutes(30));

        usuarioGateway.guardarUsuario(usuario);
        notificationGateway.enviarNotificacionRecuperacion(usuario);
    }

    public void resetPassword(String token, String nuevaContrasena) {

        if (token == null || token.trim().isEmpty()) {
            throw new RuntimeException("El token es obligatorio");
        }

        if (nuevaContrasena == null || nuevaContrasena.trim().isEmpty()) {
            throw new RuntimeException("La nueva contraseña es obligatoria");
        }

        Usuario usuario = usuarioGateway.buscarPorResetToken(token);

        if (usuario == null) {
            throw new RuntimeException("Token inválido o expirado");
        }

        if (usuario.getResetPasswordTokenExpiry() == null ||
                LocalDateTime.now().isAfter(usuario.getResetPasswordTokenExpiry())) {
            throw new RuntimeException("El token ha expirado");
        }

        usuario.setContrasena(encrypterGateway.encrypt(nuevaContrasena));
        usuario.setResetPasswordToken(null);
        usuario.setResetPasswordTokenExpiry(null);

        usuarioGateway.guardarUsuario(usuario);
    }
}