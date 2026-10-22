package com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.carrito;

import com.ecommerce.auth.infraestructure.driver_adapters.UsuarioData;
import com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.itemcarrito.ItemCarritoData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "carrito")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoData {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_carrito", length = 255)
    private String idCarrito;

    @Column(name = "usuario_id", length = 255, unique = true)
    private String usuarioId;

    @Column(name = "precio_total")
    private Double precioTotal;

    @Column(name = "estado", length = 50)
    private String estado;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarritoData> items;

    @OneToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UsuarioData usuario;
}
