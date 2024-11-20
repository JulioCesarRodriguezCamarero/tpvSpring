package org.grisu.tpvspring.servicio;

import org.grisu.tpvspring.modelo.CajaPrincipal;

import java.util.List;

public interface ICajaPrincipalServicio {
    List<CajaPrincipal> listar();
    CajaPrincipal buscar(long id);
    void agregar(CajaPrincipal cajaPrincipal);
    void eliminar(CajaPrincipal cajaPrincipal);
    CajaPrincipal buscarUsuarioActivo(boolean activo);



}
