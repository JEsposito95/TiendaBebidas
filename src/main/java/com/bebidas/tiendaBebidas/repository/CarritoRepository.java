package com.bebidas.tiendaBebidas.repository;

import com.bebidas.tiendaBebidas.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoRepository extends JpaRepository <Producto, Integer> {
    List<Producto> findByCategoria(String Categoria);
}
