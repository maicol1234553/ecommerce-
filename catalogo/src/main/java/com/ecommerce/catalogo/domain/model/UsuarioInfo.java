package com.ecommerce.catalogo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioInfo {
    // Mismos campos que serializa la entidad Usuario de auth, para que Jackson
    // pueda deserializar GET /api/ecommerce/usuario/id/{id} sin fallar.
    private String idUsuario;
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private Integer edad;
    private String numeroTelefonico;
    private Double saldo;
}
