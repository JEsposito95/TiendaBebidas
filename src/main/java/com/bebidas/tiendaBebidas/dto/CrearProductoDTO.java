package com.bebidas.tiendaBebidas.dto;

public record CrearProductoDTO(
        String nombre,
        double precio,
        int stock,
        String descripcion,
        int categoriaId
) {
}
