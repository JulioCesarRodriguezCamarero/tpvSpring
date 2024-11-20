package org.grisu.tpvspring.repositorio;

import org.grisu.tpvspring.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.cuentas WHERE u.activo = :activo")
    Usuario findByActivoWithCuentas(@Param("activo") boolean activo);

    Usuario findByNombre(String nombre);
//    Usuario findByActivo(boolean activo);
}
