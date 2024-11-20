package org.grisu.tpvspring.servicio;

import org.grisu.tpvspring.modelo.Cuenta;

import java.util.List;

public interface ICuentaServicio {
    List<Cuenta> listar();
    Cuenta buscar(long id);
    Cuenta buscarPorNombre(String nombre);
    Cuenta buscarActivo(boolean activo);
    void agregar(Cuenta cuenta);
    void eliminar(Cuenta cuenta);

}
