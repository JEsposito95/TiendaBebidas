package com.bebidas.tiendaBebidas.repository;

import com.bebidas.tiendaBebidas.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer> {
}
