package com.ecommerce.auth.infraestructure.driver_adapters;

import com.ecommerce.auth.domain.model.Usuario;
import com.ecommerce.auth.domain.model.gateway.UsuarioGateway;
import com.ecommerce.auth.infraestructure.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UsuarioDataGatewayImpl implements UsuarioGateway {

    private final UsuarioDataJpaRepository usuarioDataJpaRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        UsuarioData usuarioMapeado = usuarioMapper.toUsuarioData(usuario);

        return usuarioMapper.toUsuario(usuarioDataJpaRepository.save(usuarioMapeado));
    }

    @Override
    public Usuario buscarUsuarioPorId(String usuarioId) {
        return usuarioDataJpaRepository.findById(usuarioId)
                .map(usuarioMapper::toUsuario)
                .orElse(null);
    }

    @Override
    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarioDataJpaRepository.findByCorreo(correo)
                .map(usuarioMapper::toUsuario)
                .orElse(null);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioMapper.toUsuario(
                usuarioDataJpaRepository.save(usuarioMapper.toUsuarioData(usuario)));
    }

    @Override
    public void eliminarUsuario(String usuarioId) {
        usuarioDataJpaRepository.deleteById(usuarioId);
    }
}
