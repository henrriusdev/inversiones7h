package com.hbourgeot.inversiones7h.services;

import com.hbourgeot.inversiones7h.dao.IClienteRepo;
import com.hbourgeot.inversiones7h.entities.Cliente;
import com.hbourgeot.inversiones7h.entities.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService{
  
  @Autowired
  private IClienteRepo clienteRepo;

  public Cliente findByCedulaIdentidad(String cedula) {
      Optional<Cliente> clienteOptional = clienteRepo.findByCedulaIdentidad(cedula);
      return clienteOptional.orElse(null); 
  }

  
  public Cliente findByNombre(String nombre) {
      Optional<Cliente> clienteOptional = clienteRepo.findByNombre(nombre);
      return clienteOptional.orElse(null); 
  }

  public Cliente findByApellido(String apellido) {
      Optional<Cliente> clienteOptional = clienteRepo.findByApellido(apellido);
      return clienteOptional.orElse(null); 
  }
  

  @Autowired
  public IClienteRepo repo;

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
