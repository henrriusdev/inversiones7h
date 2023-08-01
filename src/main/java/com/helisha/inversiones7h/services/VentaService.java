package com.helisha.inversiones7h.services;

import com.helisha.inversiones7h.dao.IVentaRepo;
import com.helisha.inversiones7h.entities.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VentaService implements IVentaService{

  @Autowired
  private IVentaRepo repo;

  @Override
  public void save(Venta entity) {
    repo.save(entity);
  }

  @Override
  public Venta findById(Long id) {
    return repo.findById(id).orElse(null);
  }

  @Override
  @Transactional
  public List<Venta> findAll() {
    return (List<Venta>) repo.findAll();
  }

  @Override
  public long count() {
    return repo.count();
  }

  @Override
  public void deleteById(Long id) {
    repo.deleteById(id);
  }

  @Override
  public void delete(Venta entity) {
    repo.delete(entity);
  }
}
