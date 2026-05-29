package com.ecommerce.catalgo.domain.usecase;

import com.ecommerce.catalgo.domain.model.DetalleVenta;
import com.ecommerce.catalgo.domain.model.Producto;
import com.ecommerce.catalgo.domain.model.Recibo;
import com.ecommerce.catalgo.domain.model.Venta;
import com.ecommerce.catalgo.domain.model.gateway.ProductoGateway;
import com.ecommerce.catalgo.domain.model.gateway.ReciboGateway;
import com.ecommerce.catalgo.domain.model.gateway.VentaGateway;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
public class VentaUseCase {

    private static final double TASA_IVA = 0.19;

    private final VentaGateway ventaGateway;
    private final ProductoGateway productoGateway;
    private final ReciboGateway reciboGateway;

    public Recibo realizarVenta(Venta venta) {

        if (venta.getClienteId() == null || venta.getClienteId().isBlank()) {
            throw new RuntimeException("El clienteId es obligatorio");
        }

        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new RuntimeException("La venta debe tener al menos un producto");
        }

        List<DetalleVenta> detallesCalculados = new ArrayList<>();
        double totalVenta = 0;

        for (DetalleVenta detalle : venta.getDetalles()) {

            if (detalle.getProductoId() == null || detalle.getProductoId().isBlank()) {
                throw new RuntimeException("El productoId en el detalle es obligatorio");
            }

            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
                throw new RuntimeException("La cantidad debe ser mayor a cero para el producto: " + detalle.getProductoId());
            }

            Producto producto = productoGateway.buscarProductoPorId(detalle.getProductoId());
            if (producto == null) {
                throw new NoSuchElementException("Producto no encontrado: " + detalle.getProductoId());
            }

            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre()
                        + ". Disponible: " + producto.getStock());
            }

            // Descontar stock
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoGateway.guardarProducto(producto);

            double subtotal = producto.getPrecio() * detalle.getCantidad();
            totalVenta += subtotal;

            DetalleVenta detalleCalculado = new DetalleVenta(
                    UUID.randomUUID().toString(),
                    producto.getProductoId(),
                    producto.getNombre(),
                    detalle.getCantidad(),
                    producto.getPrecio(),
                    subtotal
            );
            detallesCalculados.add(detalleCalculado);
        }

        venta.setVentaId(UUID.randomUUID().toString());
        venta.setFecha(LocalDateTime.now());
        venta.setDetalles(detallesCalculados);
        venta.setTotal(totalVenta);
        venta.setEstado("COMPLETADA");

        Venta ventaGuardada = ventaGateway.guardarVenta(venta);

        return generarRecibo(ventaGuardada);
    }

    public Recibo generarRecibo(Venta venta) {
        double subtotal = venta.getTotal();
        double impuesto = subtotal * TASA_IVA;
        double total = subtotal + impuesto;

        Recibo recibo = new Recibo(
                UUID.randomUUID().toString(),
                venta.getVentaId(),
                venta.getClienteId(),
                LocalDateTime.now(),
                venta.getDetalles(),
                subtotal,
                impuesto,
                total
        );

        return reciboGateway.guardarRecibo(recibo);
    }

    public Recibo obtenerReciboPorVenta(String ventaId) {
        Recibo recibo = reciboGateway.buscarReciboPorVentaId(ventaId);
        if (recibo == null) {
            throw new NoSuchElementException("Recibo no encontrado para la venta: " + ventaId);
        }
        return recibo;
    }

    public Recibo obtenerReciboPorId(String reciboId) {
        Recibo recibo = reciboGateway.buscarReciboPorId(reciboId);
        if (recibo == null) {
            throw new NoSuchElementException("Recibo no encontrado: " + reciboId);
        }
        return recibo;
    }

    public List<Venta> obtenerVentasPorCliente(String clienteId) {
        return ventaGateway.buscarVentasPorCliente(clienteId);
    }
}
