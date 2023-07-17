package com.hbourgeot.inversiones7h.services;

import com.hbourgeot.inversiones7h.dao.ICompraRepo;
import com.hbourgeot.inversiones7h.entities.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService implements ICompraService{
  @Autowired
  private ICompraRepo repo;

  @Override
  public void save(Compra entity) {
    repo.save(entity);
  }

  @Override
  public Compra findById(Long id) {
    return repo.findById(id).orElse(null);
  }

  @Override
  public List<Compra> findAll() {
    return (List<Compra>) repo.findAll();
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
  public void delete(Compra entity) {
    repo.delete(entity);
  }
}
