package org.grisu.tpvspring.repositorio;

import org.grisu.tpvspring.modelo.Tique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TiqueRepositorio extends JpaRepository<Tique, Long> {
    @Query("SELECT u FROM Tique u LEFT JOIN FETCH u.lineaVentas WHERE u.activo = :activo")
    Tique findByActivoWithLAndLineaVentas(@Param("activo") boolean activo);

}
