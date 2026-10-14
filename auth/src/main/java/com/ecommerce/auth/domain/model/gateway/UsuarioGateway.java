package com.ecommerce.auth.domain.model.gateway;

import com.ecommerce.auth.domain.model.Usuario;
public interface UsuarioGateway {

    Usuario guardarUsuario(Usuario usuario);

    Usuario buscarUsuarioPorId(String usuarioId);

    Usuario buscarUsuarioPorCorreo(String correo);

    Usuario actualizarUsuario(Usuario usuario);

    void eliminarUsuario(String usuarioId);
}
