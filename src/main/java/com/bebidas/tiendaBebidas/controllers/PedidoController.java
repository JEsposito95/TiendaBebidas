package com.bebidas.tiendaBebidas.controllers;

import com.bebidas.tiendaBebidas.dto.CrearPedidoDTO;
import com.bebidas.tiendaBebidas.dto.PedidoDetalleDTO;
import com.bebidas.tiendaBebidas.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDetalleDTO> crearPedido(@RequestBody CrearPedidoDTO crearPedidoDTO) {
        PedidoDetalleDTO pedidoCreado = pedidoService.crearPedido(crearPedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCreado);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDetalleDTO>> listarPedidos() {
        List<PedidoDetalleDTO> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDetalleDTO> obtenerPedidoPorId(@PathVariable int id) {
        PedidoDetalleDTO pedido = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDetalleDTO> actualizarPedido(@PathVariable int id, @RequestBody CrearPedidoDTO actualizarPedidoDTO) {
        PedidoDetalleDTO pedidoActualizado = pedidoService.actualizarPedido(id, actualizarPedidoDTO);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable int id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/enviarWhatsapp/{id}")
    public ResponseEntity<String> enviarPedidoPorWhatsApp(@PathVariable int id) {
        String mensaje = pedidoService.enviarPedidoPorWhatsApp(id);
        return ResponseEntity.ok(mensaje);
    }

}
