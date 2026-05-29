package com.surtiana.auth.infraestructure.entry_points;


import com.surtiana.auth.domain.model.Usuario;
import com.surtiana.auth.domain.usecase.UsuarioUseCase;
import com.surtiana.auth.infraestructure.driver_adapters.jpa_repository.UsuarioData;
import com.surtiana.auth.infraestructure.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/surtiana/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioMapper usuarioMapper;

    @PostMapping("/save")
    public ResponseEntity<Usuario> saveUsauraio(@RequestBody UsuarioData usuarioData){

        Usuario usuarioGuardado = usuarioUseCase.guardarUsuario(usuarioMapper.toUsuario(usuarioData));

        return ResponseEntity.ok(usuarioGuardado);
    }

    @GetMapping("/buscar/{cedula}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable String cedula){

        return ResponseEntity.ok(
                usuarioUseCase.buscarUsuarioPorCc(cedula)
        );
    }

    @DeleteMapping("/eliminar/{cedula}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String cedula){

        usuarioUseCase.eliminarUsuarioPorCc(cedula);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioData usuarioData){

        String usuarioValidado = usuarioUseCase.login(usuarioData.getCorreo(), usuarioData.getContrasena()
        );

        return ResponseEntity.ok(usuarioValidado);
    }

}
