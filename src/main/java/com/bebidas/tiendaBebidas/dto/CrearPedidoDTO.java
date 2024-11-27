package com.bebidas.tiendaBebidas.dto;

import java.util.List;

public record CrearPedidoDTO(
        List<Long> productosIds,
        List<Integer> cantidades,
        String direccion,
        String nombreCliente
) {
}
