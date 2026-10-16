package com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.producto;

import com.ecommerce.catalogo.domain.exception.ProductoNoEncontradoException;
import com.ecommerce.catalogo.domain.model.gateway.ProductoGateway;
import com.ecommerce.catalogo.domain.model.Producto;
import com.ecommerce.catalogo.infrastructure.mapper.ProductoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductoDataGatewayImpl implements ProductoGateway {

    private final ProductoDataJpaRepository repository;
    private final ProductoMapper mapper;


    @Override
    public Producto guardarProducto(Producto producto) {
        return mapper.toProducto(repository.save(mapper.toData(producto)));
    }


    @Override
    public Producto buscarProductoPorId(String id) {
        return repository.findById(id)
                .map(mapper::toProducto)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID: " + id));
    }


    @Override
    public List<Producto> listarTodos() {

        return repository.findAll().stream()
                .map(mapper::toProducto)
                .collect(Collectors.toList());
    }


    @Override
    public void eliminarProducto(String id) {
        repository.deleteById(id);
    }
    @Override
    public Producto actualizarProducto(Producto producto) {
        return mapper.toProducto(repository.save(mapper.toData(producto)));
    }
}

