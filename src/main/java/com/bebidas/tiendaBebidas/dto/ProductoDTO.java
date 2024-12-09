package com.bebidas.tiendaBebidas.dto;

public record ProductoDTO(
        int id,
        String nombre,
        String descripcion,
        Double precio,
        String categoriaId
        ) {

}
