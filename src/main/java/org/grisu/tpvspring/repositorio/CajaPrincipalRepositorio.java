package org.grisu.tpvspring.repositorio;

import org.grisu.tpvspring.modelo.CajaPrincipal;
import org.grisu.tpvspring.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CajaPrincipalRepositorio extends JpaRepository<CajaPrincipal, Long> {
    @Query("SELECT u FROM CajaPrincipal u LEFT JOIN FETCH u.usuarios WHERE u.estaAbierta = :activo")
    CajaPrincipal findByActivoWithUsuarios(@Param("activo") boolean activo);

}
