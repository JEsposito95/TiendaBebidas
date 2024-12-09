package com.bebidas.tiendaBebidas.dto;

import java.util.List;
import java.util.Map;


public record PedidoDetalleDTO(
        int id,
        List<ProductoDTO> productos, //Lista de productos con su informacion
        Map<Integer,Integer> cantidad, // ProductoId -> cantidad
        double precioTotal,
        String estado,
        String direccion,
        String nombreCliente
) {
}
