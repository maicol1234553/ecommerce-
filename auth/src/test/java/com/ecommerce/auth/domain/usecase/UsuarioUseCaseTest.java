package com.ecommerce.auth.domain.usecase;

import com.ecommerce.auth.domain.model.Usuario;
import com.ecommerce.auth.domain.model.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UsuarioUseCaseTest {

    private final UsuarioGateway gateway = new UsuarioGateway() {

        @Override
        public Usuario guardarUsuario(Usuario usuario) {
            return usuario;
        }

        @Override
        public Usuario buscarUsuarioPorId(String usuarioId) {
            return null;
        }

        @Override
        public Usuario buscarUsuarioPorCorreo(String correo) {
            return null;
        }

        @Override
        public Usuario actualizarUsuario(Usuario usuario) {
            return usuario;
        }

        @Override
        public void eliminarUsuario(String usuarioId) {
        }
    };

    private final UsuarioUseCase useCase = new UsuarioUseCase(gateway);

    @Test
    void lanzarExcepcionCuandoLaPasswordEsInvalida() {
        Usuario usuario = new Usuario(
                "1",
                "sharik",
                "sharikgonzalezb@gmail.com",
                "",
                "admin",
                20,
                "3001234567"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> useCase.guardarUsuario(usuario)
        );
    }

    @Test
    void lanzarExcepcionCuandoElUsuarioNoExiste() {
        assertThrows(
                IllegalStateException.class,
                () -> useCase.buscarUsuarioPorId("999")
        );
    }

    @Test
    void lanzarExcepcionCuandoElTelefonoNoTiene10Digitos() {
        Usuario usuario = new Usuario(
                "1",
                "sharik",
                "sharikgonzalezb@gmail.com",
                "password123",
                "admin",
                20,
                "30012345"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> useCase.guardarUsuario(usuario)
        );
    }

    @Test
    void lanzarExcepcionCuandoElCorreoYaEstaRegistrado() {
        UsuarioGateway gatewayConCorreo = new UsuarioGateway() {

            @Override
            public Usuario guardarUsuario(Usuario usuario) {
                return usuario;
            }

            @Override
            public Usuario buscarUsuarioPorId(String usuarioId) {
                return null;
            }

            @Override
            public Usuario buscarUsuarioPorCorreo(String correo) {
                return new Usuario("2", "otro", correo, "password123", "admin", 20, "3001234567");
            }

            @Override
            public Usuario actualizarUsuario(Usuario usuario) {
                return usuario;
            }

            @Override
            public void eliminarUsuario(String usuarioId) {
            }
        };

        UsuarioUseCase useCaseConCorreo = new UsuarioUseCase(gatewayConCorreo);

        Usuario usuario = new Usuario(
                "1",
                "sharik",
                "sharikgonzalezb@gmail.com",
                "password123",
                "admin",
                20,
                "3001234567"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> useCaseConCorreo.guardarUsuario(usuario)
        );
    }
}
