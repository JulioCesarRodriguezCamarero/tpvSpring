package org.grisu.tpvspring.servicio;

import org.grisu.tpvspring.modelo.LineaVentas;

import java.util.List;

public interface ILineaVentasServicio {
    List<LineaVentas> listar();
    LineaVentas buscar(long id);
    void agregar(LineaVentas venta);
    void eliminar(LineaVentas venta);
    void agregarLineaVentas(List<LineaVentas> venta);
}
