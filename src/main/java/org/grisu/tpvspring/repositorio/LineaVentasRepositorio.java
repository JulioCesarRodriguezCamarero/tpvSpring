package org.grisu.tpvspring.repositorio;

import org.grisu.tpvspring.modelo.LineaVentas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineaVentasRepositorio extends JpaRepository<LineaVentas,Long> {

}
