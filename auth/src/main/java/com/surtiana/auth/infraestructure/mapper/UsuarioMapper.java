package com.surtiana.auth.infraestructure.mapper;

import com.surtiana.auth.domain.model.Usuario;

import com.surtiana.auth.infraestructure.driver_adapters.jpa_repository.UsuarioData;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioData tousuarioData(Usuario usuario){
        return new UsuarioData(
                usuario.getCedula(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getContrasena(),
                usuario.getTelefono(),
                usuario.getRol(),
                usuario.getResetPasswordToken(),
                usuario.getResetPasswordTokenExpiry()
        );
    }

    public Usuario toUsuario(UsuarioData usuarioData){
        return new Usuario(
                usuarioData.getCedula(),
                usuarioData.getNombre(),
                usuarioData.getCorreo(),
                usuarioData.getContrasena(),
                usuarioData.getTelefono(),
                usuarioData.getRol(),
                usuarioData.getResetPasswordToken(),
                usuarioData.getResetPasswordTokenExpiry()
        );
    }
}
