package com.bebidas.tiendaBebidas.services;

import com.bebidas.tiendaBebidas.dto.CrearPedidoDTO;
import com.bebidas.tiendaBebidas.dto.PedidoDetalleDTO;
import com.bebidas.tiendaBebidas.dto.ProductoDTO;
import com.bebidas.tiendaBebidas.models.Pedido;
import com.bebidas.tiendaBebidas.models.Producto;
import com.bebidas.tiendaBebidas.repository.PedidoRepository;
import com.bebidas.tiendaBebidas.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final ApiWhatsApp apiWhatsApp;

    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository productoRepository, ApiWhatsApp apiWhatsApp){
        this.pedidoRepository=pedidoRepository;
        this.productoRepository=productoRepository;
        this.apiWhatsApp = apiWhatsApp;
    }

    public PedidoDetalleDTO crearPedido(CrearPedidoDTO crearPedidoDTO){
        Pedido pedido= new Pedido();
        List<Producto> productos = new ArrayList<>();

        for (Long productoId : crearPedidoDTO.productosIds()){
            Producto producto = productoRepository.findById(productoId.intValue()) // Conversión de Long a Integer
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productoId));
            productos.add(producto);
        }

        pedido.setProductos(productos);
        pedido.setDireccion(crearPedidoDTO.direccion());
        pedido.setNombreCliente(crearPedidoDTO.nombreCliente());
        pedido.setPrecioTotal(productos.stream().mapToDouble(Producto::getPrecio).sum());
        pedido.setEstado("PENDIENTE");

        //Acá convierto cantidad de <Producto, Integer> a <Integer,Integer>
        Map<Producto, Integer> cantidades = new HashMap<>();
        for (Map.Entry<Integer, Integer> entrada : crearPedidoDTO.cantidades().entrySet()) {
            Producto producto = productoRepository.findById(entrada.getKey())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + entrada.getKey()));
            cantidades.put(producto, entrada.getValue());
        }
        pedido.setCantidad(cantidades);

        Map<Integer, Integer> cantidadesDTO = pedido.getCantidad().entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getId(), Map.Entry::getValue));

        pedido = pedidoRepository.save(pedido);
        return new PedidoDetalleDTO(
                pedido.getId(),
                pedido.getProductos().stream()
                        .map(producto -> new ProductoDTO(producto.getId(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio(), producto.getCategoria().getNombre()))
                        .toList(),
                cantidadesDTO,
                pedido.getPrecioTotal(),
                pedido.getEstado(),
                pedido.getDireccion(),
                pedido.getNombreCliente()
        );
    }

    public List<PedidoDetalleDTO> listarPedidos() {
        return pedidoRepository.findAll().stream()
                .map(pedido -> new PedidoDetalleDTO(
                        pedido.getId(),
                        pedido.getProductos().stream()
                                .map(producto -> new ProductoDTO(
                                        producto.getId(),
                                        producto.getNombre(),
                                        producto.getDescripcion(),
                                        producto.getPrecio(),
                                        producto.getCategoria().getNombre() // Asumiendo que tienes categoría en ProductoDTO
                                )).toList(), // Lista de ProductoDTO
                        pedido.getProductos().stream()
                                .collect(Collectors.toMap(
                                        Producto::getId,
                                        producto -> 1 // Temporalmente establecemos 1 como cantidad
                                )), // Map de ProductoId -> Cantidad
                        pedido.getPrecioTotal(),
                        pedido.getEstado(), // Estado del pedido
                        pedido.getDireccion(),
                        pedido.getNombreCliente()
                )).toList();
    }

    public Pedido actualizarEstadoPedido(int pedidoId, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + pedidoId));

        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }

    public PedidoDetalleDTO actualizarPedido(int id, CrearPedidoDTO actualizarPedidoDTO) {
        // Buscar el pedido existente
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Actualizar la información del pedido
        List<Producto> nuevosProductos = new ArrayList<>();
        for (Long productoId : actualizarPedidoDTO.productosIds()) {
            Producto producto = productoRepository.findById(productoId.intValue())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productoId));
            nuevosProductos.add(producto);
        }

        // Actualizar cantidades
        Map<Producto, Integer> nuevasCantidades = nuevosProductos.stream()
                .collect(Collectors.toMap(
                        producto -> producto,
                        producto -> actualizarPedidoDTO.cantidades().get(producto.getId())
                ));

        // Configurar el pedido
        pedidoExistente.setProductos(nuevosProductos);
        pedidoExistente.setCantidad(nuevasCantidades);
        pedidoExistente.setDireccion(actualizarPedidoDTO.direccion());
        pedidoExistente.setNombreCliente(actualizarPedidoDTO.nombreCliente());
        pedidoExistente.setPrecioTotal(nuevosProductos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum());

        // Guardar los cambios
        Pedido pedidoActualizado = pedidoRepository.save(pedidoExistente);

        // Crear el DTO de respuesta
        Map<Integer, Integer> cantidadesMap = pedidoActualizado.getCantidad().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getId(),  // Mapear Producto a su ID
                        Map.Entry::getValue              // Mantener la cantidad
                ));

        return new PedidoDetalleDTO(
                pedidoActualizado.getId(),
                pedidoActualizado.getProductos().stream()
                        .map(producto -> new ProductoDTO(
                                producto.getId(),
                                producto.getNombre(),
                                producto.getDescripcion(),
                                producto.getPrecio(),
                                producto.getCategoria().getNombre()))
                        .toList(),
                cantidadesMap,
                pedidoActualizado.getPrecioTotal(),
                pedidoActualizado.getEstado(),
                pedidoActualizado.getDireccion(),
                pedidoActualizado.getNombreCliente()
        );
    }


    public void eliminarPedido(int pedidoId) {
        if (!pedidoRepository.existsById(pedidoId)) {
            throw new RuntimeException("Pedido no encontrado: " + pedidoId);
        }

        pedidoRepository.deleteById(pedidoId);
    }

    public String enviarPedidoPorWhatsApp(int pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        String mensaje = generarMensajePedido(pedido);

        // Aquí haces la integración con la API de WhatsApp o Twilio
        boolean enviado = apiWhatsApp.enviarMensaje(mensaje, "+542625540079");

        if (enviado) {
            return "Mensaje enviado exitosamente";
        } else {
            throw new RuntimeException("No se pudo enviar el mensaje de WhatsApp");
        }
    }

    private String generarMensajePedido(Pedido pedido) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Nuevo Pedido:\n");
        mensaje.append("Cliente: ").append(pedido.getNombreCliente()).append("\n");
        mensaje.append("Dirección: ").append(pedido.getDireccion()).append("\n");
        mensaje.append("Estado: ").append(pedido.getEstado()).append("\n");
        mensaje.append("Productos:\n");

        for (Producto producto : pedido.getProductos()) {
            mensaje.append("- ").append(producto.getNombre()).append(" x ")
                    .append(pedido.getCantidad().get(producto.getId())).append("\n");
        }

        mensaje.append("Total: $").append(pedido.getPrecioTotal()).append("\n");

        return mensaje.toString();
    }


    public PedidoDetalleDTO obtenerPedidoPorId(int id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Map<Integer, Integer> cantidadesMap = pedido.getCantidad().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getId(),  // Mapear Producto a su ID
                        Map.Entry::getValue              // Mantener la cantidad
                ));

        return new PedidoDetalleDTO(
                pedido.getId(),
                pedido.getProductos().stream()
                        .map(producto -> new ProductoDTO(
                                producto.getId(),
                                producto.getNombre(),
                                producto.getDescripcion(),
                                producto.getPrecio(),
                                producto.getCategoria().getNombre()))
                        .toList(),
                cantidadesMap,
                pedido.getPrecioTotal(),
                pedido.getEstado(),
                pedido.getDireccion(),
                pedido.getNombreCliente()
        );
    }

}


