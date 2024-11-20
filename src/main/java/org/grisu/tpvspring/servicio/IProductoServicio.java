package org.grisu.tpvspring.servicio;

import org.grisu.tpvspring.modelo.Producto;

import java.util.List;

public interface IProductoServicio {
    List<Producto> listar();
    Producto buscar(long id);
    void agregar(Producto producto);
    void eliminar(Producto producto);

}
