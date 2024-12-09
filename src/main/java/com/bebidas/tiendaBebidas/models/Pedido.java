package com.bebidas.tiendaBebidas.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Entity
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Producto> productos;

    @ElementCollection
    private Map<Producto,Integer> cantidad;

    @ElementCollection
    private Map<Producto, Double> precioPorUnidad;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente; // Relación con Cliente

    private double precioTotal;
    private String direccion;
    private String estado;

    private String nombreCliente;
    private String direccionCliente;

}
