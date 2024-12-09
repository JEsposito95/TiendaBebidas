package com.bebidas.tiendaBebidas.dto;

import java.util.List;
import java.util.Map;

public record CrearPedidoDTO(
        List<Long> productosIds,
        Map<Integer,Integer> cantidades, //ProductoId -> Cantidad
        String direccion,
        String nombreCliente
) {
}
