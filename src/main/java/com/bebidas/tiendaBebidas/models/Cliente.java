package com.bebidas.tiendaBebidas.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String direccion;
    private String telefono;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

}
