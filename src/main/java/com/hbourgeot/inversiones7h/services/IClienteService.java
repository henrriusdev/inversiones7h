package com.hbourgeot.inversiones7h.services;

import com.hbourgeot.inversiones7h.entities.Cliente;

import java.util.List;

public interface IClienteService {
  void save(Cliente entity);

  Cliente findById(String s);

  List<Cliente> findAll();

  long count();

  void deleteById(String s);

  void delete(Cliente entity);
}
