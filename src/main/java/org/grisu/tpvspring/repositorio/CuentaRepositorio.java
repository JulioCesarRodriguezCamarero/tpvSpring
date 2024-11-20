package org.grisu.tpvspring.repositorio;

import org.grisu.tpvspring.modelo.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {
    Cuenta findByNombre(String nombre);
//    Cuenta findByActivo(boolean activo);
    @Query("SELECT u FROM Cuenta u LEFT JOIN FETCH u.tiques WHERE u.activo = :activo")
    Cuenta findByActivoWithTiques(@Param("activo") boolean activo);
}
