package com.ecommerce.auth.infraestructure.driver_adapters;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "usuarios")

public class UsuarioData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario", length = 255)

    private String idUsuario;
    @Column(name = "nombre", length = 255, nullable = false)
    private String nombre;
    @Column(name = "correo", length = 255, nullable = false, unique = true)
    private String correo;
    @Column(name = "password", length = 255, nullable = false)
    private String password;
    @Column(name = "rol", length = 255, nullable = false)
    private String rol;
    @Column(name = "edad", nullable = false)
    private Integer edad;
    @Column(name = "numero_telefonico", length = 10, nullable = false)
    private String numeroTelefonico;
    @Column(name = "saldo", precision = 38, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;
}
