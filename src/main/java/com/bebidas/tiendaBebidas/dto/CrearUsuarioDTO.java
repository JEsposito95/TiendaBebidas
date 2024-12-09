package com.bebidas.tiendaBebidas.dto;

public record CrearUsuarioDTO(
        String nombre,
        String email,
        String password,
        String rol
) {
}
