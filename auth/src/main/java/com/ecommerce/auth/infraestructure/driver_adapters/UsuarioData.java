package com.ecommerce.auth.infraestructure.driver_adapters;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "usuarios")

public class UsuarioData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private String idUsuario;
    @Column(length = 255, nullable = false)
    private String nombre;
    @Column(length = 255, nullable = false, unique = true)
    private String correo;
    @Column(length = 255, nullable = false)
    private String password;
    @Column(nullable = false)
    private String rol;
    @Column(nullable = false)
    private Integer edad;
    @Column(length = 10, nullable = false)
    private String numeroTelefonico;
    @Column(nullable = true)
    private Double saldo = 0.0;
}