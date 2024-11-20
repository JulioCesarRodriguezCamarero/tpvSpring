package org.grisu.tpvspring.servicio;

import org.grisu.tpvspring.modelo.Producto;
import org.grisu.tpvspring.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServicioImpl implements IProductoServicio {
    @Autowired
    private ProductoRepositorio repositorio;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listar() {
        return repositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto buscar(long id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void agregar(Producto producto) {
        repositorio.save(producto);
    }

    @Override
    @Transactional
    public void eliminar(Producto producto) {
        repositorio.delete(producto);
    }
}
