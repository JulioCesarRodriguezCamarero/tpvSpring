package org.grisu.tpvspring.servicio;

import org.grisu.tpvspring.modelo.Usuario;

import java.util.List;

public interface IUsuarioServicio {

    List<Usuario> listar();
    Usuario buscar(long id);
    Usuario buscar(String nombre);
//    Usuario buscarActivo(boolean activo);
    void agregar(Usuario usuario);
    void eliminar(Usuario usuario);
    Usuario buscarActivoConCuentas(boolean activo); // Nuevo método
}
