package com.ecommerce.catalogo.infrastructure.rest_client.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioResponse {
    private String idUsuario;
    private BigDecimal saldo;
}
