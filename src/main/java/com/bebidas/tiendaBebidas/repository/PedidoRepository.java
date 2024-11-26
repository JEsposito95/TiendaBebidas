package com.bebidas.tiendaBebidas.repository;

import com.bebidas.tiendaBebidas.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Integer> {
}