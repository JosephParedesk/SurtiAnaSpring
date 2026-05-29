package com.ecommerce.catalgo.infrastructure.entry_points;

import com.ecommerce.catalgo.domain.model.DetalleVenta;
import com.ecommerce.catalgo.domain.model.Recibo;
import com.ecommerce.catalgo.domain.model.Venta;
import com.ecommerce.catalgo.domain.usecase.VentaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ecommerce/venta")
@RequiredArgsConstructor
public class VentaController {

    private final VentaUseCase ventaUseCase;

    /**
     * POST /api/ecommerce/venta/realizar
     * Realiza una venta y devuelve el recibo generado automáticamente.
     *
     * Body ejemplo:
     * {
     *   "clienteId": "cliente-001",
     *   "detalles": [
     *     { "productoId": "prod-1", "cantidad": 2 },
     *     { "productoId": "prod-2", "cantidad": 1 }
     *   ]
     * }
     */
    @PostMapping("/realizar")
    public ResponseEntity<Recibo> realizarVenta(@RequestBody VentaRequest request) {
        Venta venta = new Venta();
        venta.setClienteId(request.getClienteId());

        List<DetalleVenta> detalles = request.getDetalles().stream()
                .map(d -> {
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setProductoId(d.getProductoId());
                    detalle.setCantidad(d.getCantidad());
                    return detalle;
                })
                .collect(Collectors.toList());

        venta.setDetalles(detalles);

        Recibo recibo = ventaUseCase.realizarVenta(venta);
        return ResponseEntity.ok(recibo);
    }

    /**
     * GET /api/ecommerce/venta/recibo/{ventaId}
     * Obtiene el recibo de una venta por su ventaId.
     */
    @GetMapping("/recibo/{ventaId}")
    public ResponseEntity<Recibo> obtenerReciboPorVenta(@PathVariable String ventaId) {
        Recibo recibo = ventaUseCase.obtenerReciboPorVenta(ventaId);
        return ResponseEntity.ok(recibo);
    }

    /**
     * GET /api/ecommerce/venta/recibo/detalle/{reciboId}
     * Obtiene un recibo específico por su reciboId.
     */
    @GetMapping("/recibo/detalle/{reciboId}")
    public ResponseEntity<Recibo> obtenerReciboPorId(@PathVariable String reciboId) {
        Recibo recibo = ventaUseCase.obtenerReciboPorId(reciboId);
        return ResponseEntity.ok(recibo);
    }

    /**
     * GET /api/ecommerce/venta/cliente/{clienteId}
     * Obtiene todas las ventas de un cliente.
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Venta>> obtenerVentasPorCliente(@PathVariable String clienteId) {
        List<Venta> ventas = ventaUseCase.obtenerVentasPorCliente(clienteId);
        return ResponseEntity.ok(ventas);
    }
}
