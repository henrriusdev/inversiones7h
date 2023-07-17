package com.hbourgeot.inversiones7h.services;

import com.hbourgeot.inversiones7h.dao.IClienteRepo;
import com.hbourgeot.inversiones7h.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService{
  @Autowired
  private IClienteRepo repo;

  @Override
  public void save(Cliente entity) {
    repo.save(entity);
  }

  @Override
  public Cliente findById(String id) {
    return repo.findById(id).orElse(null);
  }

  @Override
  public List<Cliente> findAll() {
    return (List<Cliente>) repo.findAll();
  }

  @Override
  public long count() {
    return repo.count();
  }

  @Override
  public void deleteById(String id) {
    repo.deleteById(id);
  }

  @Override
  public void delete(Cliente entity) {
    repo.delete(entity);
  }
}
