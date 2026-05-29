package com.surtiana.catalogo.infraestructure.entry_points;


import com.surtiana.catalogo.domain.model.Producto;
import com.surtiana.catalogo.domain.usecase.ProductoUseCase;
import com.surtiana.catalogo.infraestructure.driver_adapters.jpa_repository.ProductoData;
import com.surtiana.catalogo.infraestructure.mapper.ProductoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surtiana/inventario")
@RequiredArgsConstructor

public class ProductoController {

    private final ProductoUseCase productoUseCase;
    private final ProductoMapper productoMapper;

    @PostMapping("/save")
    public ResponseEntity<Producto> saveProducto(@RequestBody ProductoData productoData){

        Producto productoValidadGuardado = productoUseCase
                .guardarProducto(productoMapper.topruducto(productoData));

        if(productoValidadGuardado.getProductoId() != null){
            return new ResponseEntity<>(productoValidadGuardado, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/buscar/{productoId}")
    public ResponseEntity<Producto> buscarProducto(@PathVariable String productoId){
        System.out.println("ID recibido en controller: " + productoId);
        Producto productoEncontrado = productoUseCase.buscarProductoPorId(productoId);

        if(productoEncontrado.getProductoId() != null){
            return new ResponseEntity<>(productoEncontrado, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/eliminar/{productoId}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String productoId){

        productoUseCase.eliminarProductoPorId(productoId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
