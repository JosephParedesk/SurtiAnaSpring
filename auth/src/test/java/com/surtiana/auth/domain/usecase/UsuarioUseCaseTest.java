import com.surtiana.auth.domain.model.Usuario;
import com.surtiana.auth.domain.model.gateway.EncrypterGateway;
import com.surtiana.auth.domain.model.gateway.UsuarioGateway;
import com.surtiana.auth.domain.usecase.UsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioUseCase - Pruebas unitarias")
class UsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private EncrypterGateway encrypterGateway;

    @InjectMocks
    private UsuarioUseCase usuarioUseCase;

    private Usuario usuarioValido;

    @BeforeEach
    void setUp() {
        usuarioValido = new Usuario();
        usuarioValido.setCedula("123456");
        usuarioValido.setNombre("Juan Pérez");
        usuarioValido.setCorreo("juan@email.com");
        usuarioValido.setContrasena("password123");
    }
    @DisplayName("guardarUsuario()")
    void guardarUsuario() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("123456");
        usuario.setNombre("Johan");
        usuario.setCorreo("johantest@gmail.com");
        usuario.setContrasena("123456");
        usuario.setTelefono("3001234567");

        when(encrypterGateway.encrypt(usuario.getContrasena()))
                .thenReturn("encrypted-Password");


        Usuario usuarioGuardado = new Usuario();

        usuarioGuardado.setCedula("123456");
        usuarioGuardado.setNombre("Johan");
        usuarioGuardado.setCorreo("johantest@gmail.com");
        usuarioGuardado.setContrasena("encrypted-Password");
        usuarioGuardado.setTelefono("3001234567");

        when(usuarioGateway.guardarUsuario(any(Usuario.class)))
                .thenReturn(usuarioGuardado);


        // Act

        Usuario resultado = usuarioUseCase.guardarUsuario(usuario);

        // Assert

        assertEquals("johantest@gmail.com",
                resultado.getCorreo());

        assertEquals("encrypted-Password",
                resultado.getContrasena());

        verify(encrypterGateway).encrypt("123456");
        verify(usuarioGateway).guardarUsuario(any(Usuario.class));


    }

    @Test
    void ExcepcionCuandoCedulaEsNull() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula(null);
        usuario.setNombre("Johan");
        usuario.setCorreo("johan@gmail.com");
        usuario.setContrasena("123456");

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioUseCase.guardarUsuario(usuario)
        );

        // Assert
        assertEquals(
                "Faltan campos obligatorios o están vacíos",
                exception.getMessage()
        );
    }

    @Test
    void ExcepcionCuandoLaCedulaEstaVacia() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("");
        usuario.setNombre("Johan");
        usuario.setCorreo("johantest@gmail.com");
        usuario.setContrasena("123456");

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                usuarioUseCase.guardarUsuario(usuario)
        );

        // Assert
        assertEquals("Faltan campos obligatorios o están vacíos",
                exception.getMessage());
    }
    @Test
    void debeGuardarUsuarioConContrasenaEncriptada() {

        Usuario usuario = new Usuario();
        usuario.setCedula("123");
        usuario.setNombre("Joseph");
        usuario.setCorreo("joseph@gmail.com");
        usuario.setContrasena("123456");

        when(encrypterGateway.encrypt("123456"))
                .thenReturn("PASS_ENCRIPTADA");

        when(usuarioGateway.guardarUsuario(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioUseCase.guardarUsuario(usuario);

        assertEquals("PASS_ENCRIPTADA", resultado.getContrasena());

        verify(encrypterGateway).encrypt("123456");

        verify(usuarioGateway).guardarUsuario(any(Usuario.class));
    }
    @Test
    void ExcepcionCuandoNombreEstaVacio() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("123");
        usuario.setNombre("");
        usuario.setCorreo("johan@gmail.com");
        usuario.setContrasena("123456");

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioUseCase.guardarUsuario(usuario)
        );

        // Assert
        assertEquals(
                "Faltan campos obligatorios o están vacíos",
                exception.getMessage()
        );
    }

    @Test
    void ExcepcionCuandoElNombreEsNull() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("123456789");
        usuario.setNombre(null);
        usuario.setCorreo("johantest@gmail.com");
        usuario.setContrasena("123456");

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                usuarioUseCase.guardarUsuario(usuario)
        );

        // Assert
        assertEquals("Faltan campos obligatorios o están vacíos",
                exception.getMessage());
    }

    @Test
    void ExcepcionCuandoElCorreoEsNull() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("123456789");
        usuario.setNombre("Johan");
        usuario.setCorreo(null);
        usuario.setContrasena("123456");

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                usuarioUseCase.guardarUsuario(usuario)
        );

        // Assert
        assertEquals("Faltan campos obligatorios o están vacíos",
                exception.getMessage());
    }

    @Test
    void ExcepcionCuandoCorreoEstaVacio() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("123");
        usuario.setNombre("Johan");
        usuario.setCorreo("");
        usuario.setContrasena("123456");

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioUseCase.guardarUsuario(usuario)
        );

        // Assert
        assertEquals(
                "Faltan campos obligatorios o están vacíos",
                exception.getMessage()
        );
    }

    @Test
    void ExcepcionCuandoContrasenaEsNull() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("123");
        usuario.setNombre("Johan");
        usuario.setCorreo("johan@gmail.com");
        usuario.setContrasena(null);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioUseCase.guardarUsuario(usuario)
        );

        // Assert
        assertEquals(
                "Faltan campos obligatorios o están vacíos",
                exception.getMessage()
        );
    }

    @Test
    void ExcepcionCuandoLaContrasenaEstaVacia() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("123456789");
        usuario.setNombre("Johan");
        usuario.setCorreo("johantest@gmail.com");
        usuario.setContrasena("");

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                usuarioUseCase.guardarUsuario(usuario)
        );

        // Assert
        assertEquals("Faltan campos obligatorios o están vacíos",
                exception.getMessage());
    }

    @Test
    void buscarUsuarioPorCc() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("123456789");
        usuario.setNombre("Johan");
        usuario.setCorreo("johan@gmail.com");

        when(usuarioGateway.buscarUsuarioPorCc("123456789"))
                .thenReturn(usuario);

        // Act
        Usuario resultado = usuarioUseCase.buscarUsuarioPorCc("123456789");

        // Assert
        assertEquals("123456789", resultado.getCedula());
        assertEquals("Johan", resultado.getNombre());
        assertEquals("johan@gmail.com", resultado.getCorreo());

        verify(usuarioGateway).buscarUsuarioPorCc("123456789");

    }

    @Test
    void buscarUsuarioPorCcExcepcionCuandoNoExiste() {

        // Arrange
        when(usuarioGateway.buscarUsuarioPorCc("123456789"))
                .thenReturn(null);

        // Act
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> usuarioUseCase.buscarUsuarioPorCc("123456789")
        );

        // Assert
        assertEquals("Usuario no encontrado",
                exception.getMessage());

        verify(usuarioGateway).buscarUsuarioPorCc("123456789");
    }

    @Test
    void eliminarUsuario() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("123456789");
        usuario.setNombre("Johan");

        when(usuarioGateway.buscarUsuarioPorCc(usuario.getCedula()))
                .thenReturn(usuario);

        // Act
        usuarioUseCase.eliminarUsuarioPorCc(usuario.getCedula());

        // Assert
        verify(usuarioGateway).buscarUsuarioPorCc(usuario.getCedula());

        verify(usuarioGateway)
                .eliminarUsuarioPorCc(usuario.getCedula());
    }

    @Test
    void eliminarUsuarioExcepcionCuandoNoExiste() {

        // Arrange

        Usuario usuario = new Usuario();

        usuario.setCedula("123456789");

        when(usuarioGateway.buscarUsuarioPorCc(usuario.getCedula()))
                .thenReturn(null);

        // Act
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> usuarioUseCase.eliminarUsuarioPorCc(usuario.getCedula())
        );

        // Assert
        assertEquals(
                "Usuario no encontrado",
                exception.getMessage()
        );

        verify(usuarioGateway)
                .buscarUsuarioPorCc(usuario.getCedula());
    }

    @Test
    void login() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("123456789");
        usuario.setCorreo("johan@gmail.com");
        usuario.setContrasena("password-encriptado");

        when(usuarioGateway.buscarPorCorreo(usuario.getCorreo()))
                .thenReturn(usuario);

        when(encrypterGateway.matches(
                "123456",
                usuario.getContrasena()
        )).thenReturn(true);

        // Act
        String resultado = usuarioUseCase.login(
                usuario.getCorreo(),
                "123456"
        );

        // Assert
        assertEquals(
                "Login exitoso",
                resultado
        );

        verify(usuarioGateway)
                .buscarPorCorreo(usuario.getCorreo());

        verify(encrypterGateway)
                .matches("123456", usuario.getContrasena());
    }

    @Test
    void logiExcepcionCuandoCorreoEsNull() {

        // Arrange
        String password = "123456";

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioUseCase.login(null, password)
        );

        // Assert
        assertEquals(
                "Email y contraseña son obligatorios",
                exception.getMessage()
        );
    }

    @Test
    void logiExcepcionCuandoContraEsNull() {

        // Arrange
        String email = "johantest@gmail.com";

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioUseCase.login(email, null)
        );

        // Assert
        assertEquals(
                "Email y contraseña son obligatorios",
                exception.getMessage()
        );
    }

    @Test
    void loginExcepcionCorreoEsInvalido() {

        // Arrange
        String email = "johangmail.com";
        String password = "123456";

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioUseCase.login(email, password)
        );

        // Assert
        assertEquals(
                "Correo inválido",
                exception.getMessage()
        );
    }

    @Test
    void logiExcepcionCuandoUsuarioEsNull() {

        // Arrange
        String email = "johan@gmail.com";
        String password = "123456";

        when(usuarioGateway.buscarPorCorreo(email))
                .thenReturn(null);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioUseCase.login(email, password)
        );

        // Assert
        assertEquals(
                "Usuario no encontrado",
                exception.getMessage()
        );

        verify(usuarioGateway)
                .buscarPorCorreo(email);
    }


    @Test
    void loginExcepcionCedulaDelUsuarioEsNull() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula(null);
        usuario.setCorreo("johan@gmail.com");
        usuario.setContrasena("password-encriptado");

        when(usuarioGateway.buscarPorCorreo(usuario.getCorreo()))
                .thenReturn(usuario);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioUseCase.login(
                        usuario.getCorreo(),
                        "123456"
                )
        );

        // Assert
        assertEquals(
                "Usuario no encontrado",
                exception.getMessage()
        );

        verify(usuarioGateway)
                .buscarPorCorreo(usuario.getCorreo());
    }

    @Test
    void loginContrasenaDelUsuarioEsNull() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("123456789");
        usuario.setCorreo("johan@gmail.com");
        usuario.setContrasena(null);

        when(usuarioGateway.buscarPorCorreo(usuario.getCorreo()))
                .thenReturn(usuario);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioUseCase.login(
                        usuario.getCorreo(),
                        "123456"
                )
        );

        // Assert
        assertEquals(
                "Error en datos del usuario",
                exception.getMessage()
        );

        verify(usuarioGateway)
                .buscarPorCorreo(usuario.getCorreo());
    }
    @Test
    void loginContrasenaEsIncorrecta() {

        // Arrange
        Usuario usuario = new Usuario();

        usuario.setCedula("123456789");
        usuario.setCorreo("johan@gmail.com");
        usuario.setContrasena("password-encriptada");

        when(usuarioGateway.buscarPorCorreo("johan@gmail.com"))
                .thenReturn(usuario);

        when(encrypterGateway.matches(
                "123456",
                "password-encriptada"
        )).thenReturn(false);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioUseCase.login(
                        "johan@gmail.com",
                        "123456"
                )
        );

        // Assert
        assertEquals(
                "Contraseña incorrecta",
                exception.getMessage()
        );

        verify(usuarioGateway)
                .buscarPorCorreo("johan@gmail.com");

        verify(encrypterGateway)
                .matches("123456", "password-encriptada");
    }
}