package org.grisu.tpvspring.servicio;

import org.grisu.tpvspring.modelo.Tique;

import java.util.List;

public interface ITiqueServicio {
    List<Tique> listar();
    Tique buscar(long id);
    Tique buscarActivo(boolean activo);
    void agregar(Tique tique);
    void eliminar(Tique tique);

}
