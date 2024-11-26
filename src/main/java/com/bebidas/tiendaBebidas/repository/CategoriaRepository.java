package com.bebidas.tiendaBebidas.repository;

import com.bebidas.tiendaBebidas.models.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Carrito,Integer> {
}
