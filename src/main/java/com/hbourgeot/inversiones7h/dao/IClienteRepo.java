package com.hbourgeot.inversiones7h.dao;

import com.hbourgeot.inversiones7h.entities.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteRepo extends CrudRepository<Cliente, String> {
  @Override
  <S extends Cliente> S save(S entity);
  
  // Método para buscar un cliente por su cédula de identidad

  Optional<Cliente> findByCedulaIdentidad(String cedula);
  //metodo para buscar cliente por nombre 

  Optional<Cliente> findByNombre(String nombre);
  
  //buscamos cliente por apellido 

  Optional<Cliente> findByApellido(String apellido);
  
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
