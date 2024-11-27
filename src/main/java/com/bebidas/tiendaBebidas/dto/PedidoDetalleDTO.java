package com.bebidas.tiendaBebidas.dto;

import java.util.List;


public record PedidoDetalleDTO(
        Long id,
        List<ProductoDTO> productos,
        double precioTotal,
        String estado,
        String direccion,
        String nombreCliente
) {
}
