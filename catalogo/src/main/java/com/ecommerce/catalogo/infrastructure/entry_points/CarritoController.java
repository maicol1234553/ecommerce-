package com.ecommerce.catalogo.infrastructure.entry_points;

import com.ecommerce.catalogo.domain.exception.CarritoNoFoundException;
import com.ecommerce.catalogo.domain.exception.ProductoNoEncontradoException;
import com.ecommerce.catalogo.domain.exception.UsuarioNoEncontradoException;
import com.ecommerce.catalogo.domain.model.Carrito;
import com.ecommerce.catalogo.domain.model.ItemCarrito;
import com.ecommerce.catalogo.domain.usecase.CarritoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecommerce/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoUseCase carritoUseCase;

    @PostMapping("/{usuarioId}/agregar")
    public ResponseEntity<?> agregarItem(@PathVariable String usuarioId, @RequestBody ItemCarrito item) {
        try {
            return ResponseEntity.ok(carritoUseCase.agregarProductoAlCarrito(usuarioId, item));
        }catch (UsuarioNoEncontradoException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }catch (ProductoNoEncontradoException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/{usuarioId}/ver")
    public ResponseEntity<Carrito> verCarrito(@PathVariable String usuarioId) {
        return ResponseEntity.ok(carritoUseCase.verCarrito(usuarioId));
    }

    @DeleteMapping("/eliminar/{productoId}")
    public ResponseEntity<Carrito> eliminarItemDelCarrito(
            @RequestParam String usuarioId,
            @PathVariable String productoId
    ) {
        try {
            Carrito carritoActualizado = carritoUseCase.eliminarProductoDelCarrito(usuarioId, productoId);
            return ResponseEntity.ok(carritoActualizado);
        }catch (CarritoNoFoundException errorMessage){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{usuarioId}/venta")
    public ResponseEntity<String> realizarVenta(@PathVariable String usuarioId) {
        try {
            String mensaje = carritoUseCase.realizarVenta(usuarioId);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.ok("Error al realizar la venta: " + e.getMessage());
        }
    }
}