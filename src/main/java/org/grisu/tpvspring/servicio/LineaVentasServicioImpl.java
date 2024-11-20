package org.grisu.tpvspring.servicio;

import org.grisu.tpvspring.modelo.LineaVentas;
import org.grisu.tpvspring.repositorio.LineaVentasRepositorio;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LineaVentasServicioImpl implements ILineaVentasServicio {
    @Autowired
    private LineaVentasRepositorio repositorio;

    @Override
    @Transactional(readOnly = true)
    public List<LineaVentas> listar() {
        return repositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public LineaVentas buscar(long id) {
//        LineaVentas ventas = repositorio.findById(id).orElse(null);
//        if (ventas != null) {
//            Hibernate.initialize(ventas);
//        }
        return repositorio.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void agregar(LineaVentas venta) {
        repositorio.save(venta);
    }

    @Override
    @Transactional
    public void eliminar(LineaVentas venta) {
        repositorio.delete(venta);
    }

    @Override
    @Transactional
    public void agregarLineaVentas(List<LineaVentas> venta) {
        repositorio.saveAll(venta);
    }
}
