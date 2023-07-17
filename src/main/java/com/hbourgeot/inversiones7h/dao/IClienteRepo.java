package com.hbourgeot.inversiones7h.dao;

import com.hbourgeot.inversiones7h.entities.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteRepo extends CrudRepository<Cliente, String> {
  @Override
  <S extends Cliente> S save(S entity);

  @Override
  Optional<Cliente> findById(String s);

  @Override
  Iterable<Cliente> findAll();

  @Override
  long count();

  @Override
  void deleteById(String s);

  @Override
  void delete(Cliente entity);
}
