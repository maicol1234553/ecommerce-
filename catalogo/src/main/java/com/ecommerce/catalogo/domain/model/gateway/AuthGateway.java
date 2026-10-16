package com.ecommerce.catalogo.domain.model.gateway;

import com.ecommerce.catalogo.domain.model.UsuarioInfo;

import java.math.BigDecimal;

public interface AuthGateway {
    boolean existeUsuario(String usuarioId);
    UsuarioInfo consultarDatosUsuario(String usuarioId);
    void solicitarDescuentoSaldo(String usuarioId, BigDecimal montoTotal);
}