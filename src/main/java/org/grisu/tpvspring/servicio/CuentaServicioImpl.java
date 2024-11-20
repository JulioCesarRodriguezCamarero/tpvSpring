package org.grisu.tpvspring.servicio;

import org.grisu.tpvspring.modelo.Cuenta;
import org.grisu.tpvspring.repositorio.CuentaRepositorio;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CuentaServicioImpl implements ICuentaServicio {
    @Autowired
    private CuentaRepositorio repositorio;

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> listar() {
        return repositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta buscar(long id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta buscarPorNombre(String nombre) {
        return repositorio.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta buscarActivo(boolean activo) {
        return repositorio.findByActivoWithTiques(activo);
    }

    @Override
    @Transactional
    public void agregar(Cuenta cuenta) {
repositorio.save(cuenta);
    }

    @Override
    @Transactional
    public void eliminar(Cuenta cuenta) {
        repositorio.delete(cuenta);
    }

}
