package com.surtiana.auth.domain.model.gateway;


import com.surtiana.auth.domain.model.Usuario;

public interface UsuarioGateway {

Usuario guardarUsuario(Usuario usuario);

Usuario buscarUsuarioPorCc (String cedula);

void eliminarUsuarioPorCc (String cedula);

Usuario buscarPorCorreo(String correo);

Usuario buscarPorResetToken(String token);


}
