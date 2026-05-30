package com.surtiana.catalogo.infraestructure.entry_points;


import com.surtiana.catalogo.domain.model.DetalleVenta;
import com.surtiana.catalogo.domain.model.Recibo;
import com.surtiana.catalogo.domain.model.Venta;
import com.surtiana.catalogo.domain.usecase.VentaUseCase;
import com.surtiana.catalogo.infraestructure.mapper.VentaMapper;
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
    private final VentaMapper ventaMapper;

    @PostMapping("/realizar")
    public ResponseEntity<Recibo> realizarVenta(@RequestBody VentaRequest request) {
        Recibo recibo = ventaUseCase.realizarVenta(ventaMapper.toVenta(request));
        return ResponseEntity.ok(recibo);
    }

    @GetMapping("/recibo/{ventaId}")
    public ResponseEntity<Recibo> obtenerReciboPorVenta(@PathVariable String ventaId) {
        return ResponseEntity.ok(ventaUseCase.obtenerReciboPorVenta(ventaId));
    }

    @GetMapping("/recibo/detalle/{reciboId}")
    public ResponseEntity<Recibo> obtenerReciboPorId(@PathVariable String reciboId) {
        return ResponseEntity.ok(ventaUseCase.obtenerReciboPorId(reciboId));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Venta>> obtenerVentasPorCliente(@PathVariable String clienteId) {
        return ResponseEntity.ok(ventaUseCase.obtenerVentasPorCliente(clienteId));
    }
}
