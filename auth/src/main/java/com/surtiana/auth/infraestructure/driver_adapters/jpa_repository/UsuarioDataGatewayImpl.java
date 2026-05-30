package com.surtiana.auth.infraestructure.driver_adapters.jpa_repository;

import com.surtiana.auth.domain.model.Usuario;
import com.surtiana.auth.domain.model.gateway.UsuarioGateway;
import com.surtiana.auth.infraestructure.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsuarioDataGatewayImpl implements UsuarioGateway {

    private final UsuarioDataJpaRepository usuarioDataJpaRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        UsuarioData usuarioDataGuardar = usuarioMapper.tousuarioData(usuario);

        return usuarioMapper.toUsuario(usuarioDataJpaRepository.save(usuarioDataGuardar));
    }

    @Override
    public Usuario buscarUsuarioPorCc(String cedula) {

        UsuarioData usarioEncontrado = usuarioDataJpaRepository.findById(cedula).orElse(null);

        if(usarioEncontrado == null){
            return null;
        }

        return usuarioMapper.toUsuario(usarioEncontrado);
    }


    @Override
    public void eliminarUsuarioPorCc(String cedula) {
        usuarioDataJpaRepository.deleteById(cedula);
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        return usuarioDataJpaRepository.findByCorreo(correo).map(usuarioMapper::toUsuario).orElse(null);
    }

    @Override
    public Usuario buscarPorResetToken(String token) {
        return usuarioDataJpaRepository.findByResetPasswordToken(token).map(usuarioMapper::toUsuario).orElse(null);
    }


}
