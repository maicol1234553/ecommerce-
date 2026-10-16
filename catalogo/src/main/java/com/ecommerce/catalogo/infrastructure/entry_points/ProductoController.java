package com.ecommerce.catalogo.infrastructure.entry_points;

import com.ecommerce.catalogo.domain.model.Producto;
import com.ecommerce.catalogo.domain.usecase.ProductoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ecommerce/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoUseCase useCase;


    @PostMapping("/guardar")
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(useCase.guardarProducto(producto));
    }


    @GetMapping("/{id}/buscar")
    public ResponseEntity<Producto>buscarProductoPorId(@PathVariable String id) {
        return ResponseEntity.ok(useCase.buscarProductoPorId(id));
    }


    @GetMapping("/{id}/lista")
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(useCase.listarTodos());
    }


    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String id) {
        useCase.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/actualizar")
    public ResponseEntity<Producto> actualizarProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(useCase.actualizarProducto(producto));
    }
}
