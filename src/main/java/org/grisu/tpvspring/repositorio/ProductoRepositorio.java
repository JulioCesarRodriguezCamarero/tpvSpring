package org.grisu.tpvspring.repositorio;

import org.grisu.tpvspring.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
}
