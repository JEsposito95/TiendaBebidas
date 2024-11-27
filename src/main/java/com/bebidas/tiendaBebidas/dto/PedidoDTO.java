package com.bebidas.tiendaBebidas.dto;

import com.bebidas.tiendaBebidas.models.Producto;

import java.util.List;

public record PedidoDTO(
        int id,
        List<Producto> productos, //ver si de verdad va
        double precioTotal,
        String estado
) {
}
