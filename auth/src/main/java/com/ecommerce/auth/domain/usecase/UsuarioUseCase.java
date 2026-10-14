package com.ecommerce.auth.domain.usecase;
import com.ecommerce.auth.domain.model.gateway.EncrypterGateway;
import com.ecommerce.auth.domain.model.Usuario;
import com.ecommerce.auth.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class UsuarioUseCase {

    private final UsuarioGateway usuarioGateway;
    private final EncrypterGateway encrypterGateway;

    public Usuario guardarUsuario(Usuario usuario) {
        validarUsuario(usuario);
        validarCorreoDisponible(usuario);

        String passwordEncrypt = encrypterGateway.encrypt(usuario.getPassword());
        usuario.setPassword(passwordEncrypt);

        return usuarioGateway.guardarUsuario(usuario);
    }

    public String loginUsuario(String correo, String password) {

        Usuario usuarioLogueo = usuarioGateway.buscarUsuarioPorCorreo(correo);

        if (usuarioLogueo == null) {
            return "Usuario no encontrado";
        }

        if (encrypterGateway.checkPass(password, usuarioLogueo.getPassword())) {
            return "Logueo EXITOSO";
        }

        return "Credenciales invalidas";
    }

    public Usuario buscarUsuarioPorId(String usuarioId) {

        return switch (usuarioGateway.buscarUsuarioPorId(usuarioId)) {
            case null -> throw new IllegalStateException("Usuario no encontrado con id: " + usuarioId);
            case Usuario usuario -> usuario;
        };

    }
    public Usuario buscarUsuarioPorCorreo(String correo) {

        return switch (usuarioGateway.buscarUsuarioPorCorreo(correo)){
            case null -> throw new IllegalStateException("Usuario no encontrado con correo: " + correo);
            case Usuario usuario -> usuario;
        };
    }

    public Usuario actualizarUsuario(Usuario usuario) {

        validarUsuario(usuario);

        buscarUsuarioPorId(usuario.getIdUsuario());

        validarCorreoDisponible(usuario);

        return usuarioGateway.actualizarUsuario(usuario);
    }

    public void eliminarUsuario(String usuarioId) {

        buscarUsuarioPorId(usuarioId);

        usuarioGateway.eliminarUsuario(usuarioId);
    }

    private void validarUsuario(Usuario usuario) {

        switch (usuario) {
            case null -> throw new IllegalArgumentException("El usuario es obligatorio");
            case Usuario u when u.getNombre() == null || u.getNombre().isBlank()
                    -> throw new IllegalArgumentException("El nombre es obligatorio");
            case Usuario u when u.getCorreo() == null || !u.getCorreo().contains("@")
                    -> throw new IllegalArgumentException("El correo no es válido");
            case Usuario u when u.getPassword() == null || u.getPassword().length() < 8
                    -> throw new IllegalArgumentException("La contraseña debe tener mínimo 8 caracteres");
            case Usuario u when u.getRol() == null || u.getRol().isBlank()
                    -> throw new IllegalArgumentException("El rol es obligatorio");
            case Usuario u when u.getEdad() == null || u.getEdad() < 18
                    -> throw new IllegalArgumentException("Usuario menor de edad");
            case Usuario u when u.getEdad() == null || u.getEdad() > 122
                    -> throw new IllegalArgumentException("no puede tener mas de 122 años ");
            case Usuario u when u.getNumeroTelefonico() == null || u.getNumeroTelefonico().isBlank()
                    -> throw new IllegalArgumentException("El número telefónico es obligatorio");
            case Usuario u when !u.getNumeroTelefonico().matches("\\d{10}")
                    -> throw new IllegalArgumentException("El número telefónico debe tener 10 dígitos");
            default -> { }
        }
    }

    private void validarCorreoDisponible(Usuario usuario) {

        switch (usuarioGateway.buscarUsuarioPorCorreo(usuario.getCorreo())) {
            case null -> { }
            case Usuario existente when !existente.getIdUsuario().equals(usuario.getIdUsuario())
                    -> throw new IllegalArgumentException("El correo ya está registrado");
            default -> { }
        }
    }
}
