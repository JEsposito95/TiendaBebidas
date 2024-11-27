package com.bebidas.tiendaBebidas.dto;

public record ProductoDTO(
        int id,
        String nombre,
        Double precio,
        String categoria,
        String descripcion) {
}
