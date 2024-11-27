package com.bebidas.tiendaBebidas.dto;

public record ProductoDTO(
        Long id,
        String nombre,
        Double precio,
        CategoriaDTO categoria,
        String descripcion) {
}
