package com.ecommerce.catalogo.infrastructure.driver_adapters.jpa_repository.carrito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CarritoDataJpaRepository extends JpaRepository<CarritoData, String> {

    Optional<CarritoData> findByUsuarioId(String usuarioId);

    @Transactional
    void deleteByUsuarioId(String usuarioId);
}
