package org.grisu.tpvspring.servicio;

import org.grisu.tpvspring.modelo.Tique;
import org.grisu.tpvspring.repositorio.TiqueRepositorio;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TiqueServicioImpl implements ITiqueServicio {

    @Autowired
    private TiqueRepositorio repositorio;

    @Override
    @Transactional(readOnly = true)
    public List<Tique> listar() {
        return repositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Tique buscar(long id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    public Tique buscarActivo(boolean activo) {
        return repositorio.findByActivoWithLAndLineaVentas(activo);
    }

    @Override
    @Transactional
    public void agregar(Tique tique) {
        repositorio.save(tique);
    }

    @Override
    @Transactional
    public void eliminar(Tique tique) {
        repositorio.delete(tique);
    }
}
