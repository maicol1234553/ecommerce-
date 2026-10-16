package com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.carrito;

import com.ecommerce.catalogo.domain.model.Carrito;
import com.ecommerce.catalogo.domain.model.gateway.CarritoGateway;
import com.ecommerce.catalogo.infrastructure.mapper.CarritoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CarritoDataGatewayImpl implements CarritoGateway {

    private final CarritoDataJpaRepository repository;
    private final CarritoMapper mapper;

    @Override
    public Carrito guardar(Carrito carrito) {
        var entity = mapper.toData(carrito);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Carrito buscarPorUsuarioId(String usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public void eliminarCarritoByUsuarioId(String usuarioId) {
        repository.deleteByUsuarioId(usuarioId);
    }
}