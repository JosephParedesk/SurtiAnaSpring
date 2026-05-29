package com.ecommerce.catalgo.infrastructure.driver_adapters.jpa_repository.ventas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReciboDataJpaRepository extends JpaRepository<ReciboData, String> {
    Optional<ReciboData> findByVentaId(String ventaId);
}
