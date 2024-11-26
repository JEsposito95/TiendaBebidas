package com.bebidas.tiendaBebidas.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String descpripcion;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;
}
