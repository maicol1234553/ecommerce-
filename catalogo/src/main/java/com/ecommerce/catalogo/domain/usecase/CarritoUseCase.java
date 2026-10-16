package com.ecommerce.catalogo.domain.usecase;

import com.ecommerce.catalogo.domain.exception.CarritoNoFoundException;
import com.ecommerce.catalogo.domain.exception.UsuarioNoEncontradoException;
import com.ecommerce.catalogo.domain.model.Carrito;
import com.ecommerce.catalogo.domain.model.ItemCarrito;
import com.ecommerce.catalogo.domain.model.Producto;
import com.ecommerce.catalogo.domain.model.gateway.CarritoGateway;
import com.ecommerce.catalogo.domain.model.gateway.AuthGateway;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.ecommerce.catalogo.domain.model.UsuarioInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CarritoUseCase {

    private final CarritoGateway carritoGateway;
    private final ProductoUseCase productoUseCase;
    private final AuthGateway authGateway;


    public Carrito agregarProductoAlCarrito(String usuarioId, ItemCarrito itemCarrito) {

        if (!authGateway.existeUsuario(usuarioId)) {
            throw new  UsuarioNoEncontradoException("Usuario no encontrado con ID: " + usuarioId);
        }

        Producto producto = productoUseCase.buscarProductoPorId(itemCarrito.getProductoId());

        itemCarrito.setNombreProducto(producto.getNombre());
        itemCarrito.setPrecioUnitario(producto.getPrecio());
        itemCarrito.setSubtotal(producto.getPrecio() * itemCarrito.getCantidad());

        Carrito carrito = carritoGateway.buscarPorUsuarioId(usuarioId);


        if (carrito == null) {
            carrito = new Carrito();
            carrito.setUsuarioId(usuarioId);
            carrito.setItems(new ArrayList<>());
        }

        Optional<ItemCarrito> existente = carrito.getItems()
                .stream()
                .filter(itemCarrito1 -> itemCarrito1.getProductoId().equals(itemCarrito.getProductoId()))
                .findFirst();

        if (existente.isPresent()) {
            ItemCarrito itemExistente = existente.get();
            itemExistente.setCantidad(itemExistente.getCantidad() + itemCarrito.getCantidad());
            itemExistente.setSubtotal(itemExistente.getPrecioUnitario() * itemExistente.getCantidad());
        } else {
            carrito.getItems().add(itemCarrito);
        }

        double total = carrito.getItems().stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();

        carrito.setPrecioTotal(total);

        return carritoGateway.guardar(carrito);
    }

    public Carrito verCarrito(String usuarioId) {
        return carritoGateway.buscarPorUsuarioId(usuarioId);
    }


    public Carrito eliminarProductoDelCarrito(String usuarioId, String productoId) {
        System.out.println("Eliminando producto ID: " + productoId + " del carrito del usuario ID: " + usuarioId);
        Carrito carrito = carritoGateway.buscarPorUsuarioId(usuarioId);

        if (carrito == null) {
            throw new CarritoNoFoundException("Carrito no encontrado para el usuario ID: " + usuarioId);
        }

        List<ItemCarrito> itemsFiltrados = carrito.getItems()
                .stream()
                .filter(item -> !item.getProductoId().equals(productoId))
                .collect(Collectors.toList());

        carrito.setItems(itemsFiltrados);

        double total = itemsFiltrados.stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();

        carrito.setPrecioTotal(total);

        return carritoGateway.guardar(carrito);
    }

    @Transactional
    public String realizarVenta(String usuarioId) {
        Carrito carrito = carritoGateway.buscarPorUsuarioId(usuarioId);

        if (carrito == null || carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío o no existe para el usuario.");
        }

        if ("COMPRADO".equals(carrito.getEstado())) {
            throw new RuntimeException("Este carrito ya fue procesado.");
        }

        // 1. VALIDACIÓN DE STOCK Y CÁLCULO DEL TOTAL
        double totalCompra = 0.0;

        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = productoUseCase.buscarProductoPorId(item.getProductoId());

            if (producto == null) {
                throw new RuntimeException("Producto con ID " + item.getProductoId() + " no encontrado.");
            }

            if (producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("Compra rechazada: Stock insuficiente para " + producto.getNombre());
            }

            // Se calcula el total acumulado usando el precio real del producto
            totalCompra += producto.getPrecio() * item.getCantidad();
        }

        // 2. CONSULTA DE SALDO EN AUTH
        UsuarioInfo usuario = authGateway.consultarDatosUsuario(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("El usuario no existe.");
        }

        if (usuario.getSaldo() < totalCompra) {
            throw new RuntimeException("Compra rechazada: Saldo insuficiente.");
        }

        // 3. EJECUTAR COBRO EN AUTH
        authGateway.solicitarDescuentoSaldo(usuarioId, totalCompra);

        // 4. DESCONTAR INVENTARIO
        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = productoUseCase.buscarProductoPorId(item.getProductoId());
            producto.setStock(producto.getStock() - item.getCantidad());
            productoUseCase.guardarProducto(producto);
        }

        // 5. ACTUALIZAR ESTADO Y TOTAL DEL CARRITO
        carrito.setPrecioTotal(totalCompra);
        carrito.setEstado("COMPRADO");
        carritoGateway.guardar(carrito);

        return "La compra fue realizada exitosamente";
    }
}
