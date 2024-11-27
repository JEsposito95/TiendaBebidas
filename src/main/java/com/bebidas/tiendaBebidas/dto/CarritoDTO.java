package com.bebidas.tiendaBebidas.dto;

import com.bebidas.tiendaBebidas.models.Producto;

import java.util.List;

public record CarritoDTO(
        int id,
        List<Producto> productos,
        double precioTotal
) {
}
