package com.surtiana.catalogo.infraestructure.mapper;


import com.surtiana.catalogo.domain.model.DetalleVenta;
import com.surtiana.catalogo.domain.model.Venta;
import com.surtiana.catalogo.infraestructure.driver_adapters.jpa_repository.ventas.DetalleVentaData;
import com.surtiana.catalogo.infraestructure.driver_adapters.jpa_repository.ventas.VentaData;
import com.surtiana.catalogo.infraestructure.entry_points.VentaRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class VentaMapper {

    public Venta toVenta(VentaRequest request) {
        if (request == null) return null;

        List<DetalleVenta> detalles = null;
        if (request.getDetalles() != null) {
            detalles = request.getDetalles().stream()
                    .map(d -> new DetalleVenta(
                            null,
                            d.getProductoId(),
                            null,
                            d.getCantidad(),
                            null,
                            null
                    ))
                    .collect(Collectors.toList());
        }

        Venta venta = new Venta();
        venta.setClienteId(request.getClienteId());
        venta.setDetalles(detalles);
        return venta;
    }


    public VentaData toVentaData(Venta venta) {
        VentaData ventaData = new VentaData();
        ventaData.setVentaId(venta.getVentaId());
        ventaData.setClienteId(venta.getClienteId());
        ventaData.setFecha(venta.getFecha());
        ventaData.setTotal(venta.getTotal());
        ventaData.setEstado(venta.getEstado());

        if (venta.getDetalles() != null) {
            List<DetalleVentaData> detallesData = venta.getDetalles().stream()
                    .map(d -> {
                        DetalleVentaData dd = new DetalleVentaData();
                        dd.setDetalleId(d.getDetalleId());
                        dd.setProductoId(d.getProductoId());
                        dd.setNombreProducto(d.getNombreProducto());
                        dd.setCantidad(d.getCantidad());
                        dd.setPrecioUnitario(d.getPrecioUnitario());
                        dd.setSubtotal(d.getSubtotal());
                        dd.setVenta(ventaData);
                        return dd;
                    })
                    .collect(Collectors.toList());
            ventaData.setDetalles(detallesData);
        }

        return ventaData;
    }

    public Venta toVenta(VentaData ventaData) {
        if (ventaData == null) return null;

        List<DetalleVenta> detalles = null;
        if (ventaData.getDetalles() != null) {
            detalles = ventaData.getDetalles().stream()
                    .map(dd -> new DetalleVenta(
                            dd.getDetalleId(),
                            dd.getProductoId(),
                            dd.getNombreProducto(),
                            dd.getCantidad(),
                            dd.getPrecioUnitario(),
                            dd.getSubtotal()
                    ))
                    .collect(Collectors.toList());
        }

        return new Venta(
                ventaData.getVentaId(),
                ventaData.getClienteId(),
                ventaData.getFecha(),
                detalles,
                ventaData.getTotal(),
                ventaData.getEstado()
        );
    }
}