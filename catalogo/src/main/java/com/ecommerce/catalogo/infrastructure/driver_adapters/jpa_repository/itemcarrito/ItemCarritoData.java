package com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.itemcarrito;

import com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.carrito.CarritoData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_carrito")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarritoData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idItemCarrito;
    private String productoId;
    private String nombreProducto;
    private Double precioUnitario;
    private Integer cantidad;
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "id_carrito")
    private CarritoData carrito;
}
