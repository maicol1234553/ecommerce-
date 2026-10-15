package com.ecommerce.catalogo.domain.model.usecase;

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


    public Producto obtenerProducto(String id) {
        try {
            return productoGateway.buscarProductoPorId(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new Producto();
        }
    }


    public List<Producto> obtenerTodos() {
        return productoGateway.listarTodos();
    }


    public void eliminarProducto(String id) {
        productoGateway.eliminarProducto(id);
    }
}
