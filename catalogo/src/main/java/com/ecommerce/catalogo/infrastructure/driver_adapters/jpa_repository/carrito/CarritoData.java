package com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.carrito;

import com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.itemcarrito.ItemCarritoData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "carrito")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoData {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idCarrito;

    private String usuarioId;

    private Double precioTotal;

    private String estado;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarritoData> items;
}
