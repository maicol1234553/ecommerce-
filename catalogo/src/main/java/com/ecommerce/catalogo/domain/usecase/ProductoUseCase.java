package com.ecommerce.catalogo.domain.usecase;

import com.ecommerce.catalogo.domain.model.Producto;
import com.ecommerce.catalogo.domain.model.gateway.ProductoGateway;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class ProductoUseCase {

    private final ProductoGateway productoGateway;


    public Producto guardarProducto(Producto producto) {
        return productoGateway.guardarProducto(producto);
    }


    public Producto buscarProductoPorId(String id) {
        return productoGateway.buscarProductoPorId(id);
    }


    public List<Producto> listarTodos() {

        return productoGateway.listarTodos();
    }


    public void eliminarProducto(String id) {

        productoGateway.eliminarProducto(id);
    }
    public Producto actualizarProducto(Producto producto) {

        return productoGateway.actualizarProducto(producto);
    }
}
