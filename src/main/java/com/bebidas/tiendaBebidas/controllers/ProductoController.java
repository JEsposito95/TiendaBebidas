package com.bebidas.tiendaBebidas.controllers;

import com.bebidas.tiendaBebidas.dto.CrearProductoDTO;
import com.bebidas.tiendaBebidas.dto.ProductoDTO;
import com.bebidas.tiendaBebidas.models.Producto;
import com.bebidas.tiendaBebidas.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody CrearProductoDTO crearProductoDTO){
        ProductoDTO productoCreado = productoService.crearProducto(crearProductoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    @GetMapping
    public List<ProductoDTO> listarProductos(){
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable int id) {
        ProductoDTO prodcucto = productoService.buscarProductoPorId(id);
        return ResponseEntity.ok(prodcucto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto (@PathVariable int id, @RequestBody CrearProductoDTO crearProductoDTO){
        ProductoDTO productoActualizado = productoService.actualizarProducto(id, crearProductoDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id{")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int id){
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
