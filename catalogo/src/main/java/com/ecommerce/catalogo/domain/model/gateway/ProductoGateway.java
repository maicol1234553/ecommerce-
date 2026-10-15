package com.ecommerce.catalogo.domain.model.gateway;
import com.ecommerce.catalogo.domain.model.Producto;
import java.util.List;
public interface ProductoGateway {

    Producto guardarProducto (Producto producto);
    Producto buscarProductoPorId(String productoId);
    List<Producto> listarTodos();
    Producto actualizarProducto(Producto producto);
    void eliminarProducto(String productoId);

}
