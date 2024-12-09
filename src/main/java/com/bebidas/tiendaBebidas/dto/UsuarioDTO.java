package com.bebidas.tiendaBebidas.dto;

public record UsuarioDTO(
        int id,
        String nombre,
        String email,
        String rol
) {
}
