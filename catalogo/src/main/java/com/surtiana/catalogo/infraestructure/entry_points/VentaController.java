package com.surtiana.catalogo.infraestructure.entry_points;


import com.surtiana.catalogo.domain.model.DetalleVenta;
import com.surtiana.catalogo.domain.model.Recibo;
import com.surtiana.catalogo.domain.model.Venta;
import com.surtiana.catalogo.domain.usecase.VentaUseCase;
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


    @GetMapping("/recibo/{ventaId}")
    public ResponseEntity<Recibo> obtenerReciboPorVenta(@PathVariable String ventaId) {
        Recibo recibo = ventaUseCase.obtenerReciboPorVenta(ventaId);
        return ResponseEntity.ok(recibo);
    }


    @GetMapping("/recibo/detalle/{reciboId}")
    public ResponseEntity<Recibo> obtenerReciboPorId(@PathVariable String reciboId) {
        Recibo recibo = ventaUseCase.obtenerReciboPorId(reciboId);
        return ResponseEntity.ok(recibo);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Venta>> obtenerVentasPorCliente(@PathVariable String clienteId) {
        List<Venta> ventas = ventaUseCase.obtenerVentasPorCliente(clienteId);
        return ResponseEntity.ok(ventas);
    }
}
