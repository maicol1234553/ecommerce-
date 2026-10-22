package com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.itemcarrito;

import com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.carrito.CarritoData;
import com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.producto.ProductoData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "item_carrito")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarritoData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_item_carrito", length = 255)
    private String idItemCarrito;

    @Column(name = "producto_id", length = 255)
    private String productoId;

    @Column(name = "nombre_producto", length = 255)
    private String nombreProducto;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "subtotal")
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "id_carrito")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CarritoData carrito;

    @ManyToOne
    @JoinColumn(name = "producto_id", insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ProductoData producto;
}
