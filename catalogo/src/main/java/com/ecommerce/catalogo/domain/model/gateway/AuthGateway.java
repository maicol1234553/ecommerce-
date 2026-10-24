package com.ecommerce.catalogo.domain.model.gateway;

import java.math.BigDecimal;

public interface AuthGateway {
    boolean existeUsuario(String usuarioId);
    BigDecimal consultarSaldo(String usuarioId);
    void solicitarDescuentoSaldo(String usuarioId, BigDecimal montoTotal);
}
