package com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.producto;

import com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.itemcarrito.ItemCarritoData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 255)
    private String id;

    @Column(nullable = false, name = "nombre", length = 100)
    private String nombre;

    @Column(nullable = false, name = "descripcion", length = 500)
    private String descripcion;

    @Column(nullable = false, name = "precio")
    private Double precio;

    @Column(nullable = false, name = "stock")
    private Integer stock;

    @Column(nullable = false, name = "imagen_url", length = 255)
    private String imagenUrl;

    @OneToMany(mappedBy = "producto")
    private List<ItemCarritoData> items = new ArrayList<>();

}
