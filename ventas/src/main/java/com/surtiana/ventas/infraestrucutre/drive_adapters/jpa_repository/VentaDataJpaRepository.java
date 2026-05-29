package com.ecommerce.catalgo.infrastructure.driver_adapters.jpa_repository.ventas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VentaDataJpaRepository extends JpaRepository<VentaData, String> {
    List<VentaData> findByClienteId(String clienteId);
}
