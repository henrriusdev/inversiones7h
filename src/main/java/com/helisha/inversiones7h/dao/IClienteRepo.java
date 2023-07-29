package com.helisha.inversiones7h.dao;

import com.helisha.inversiones7h.entities.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteRepo extends CrudRepository<Cliente, String> {
  @Override
    @NonNull
  <S extends Cliente> S save(@NonNull S entity);
  
  // Método para buscar un cliente por su cédula de identidad

  Optional<Cliente> findByCedulaIdentidad(String cedula);
  //metodo para buscar cliente por nombre 

  Optional<Cliente> findByNombre(String nombre);
  
  //buscamos cliente por apellido 

  Optional<Cliente> findByApellido(String apellido);
  
  @Override
  @NonNull
  Optional<Cliente> findById(@NonNull String s);

  @Override
  @NonNull
  Iterable<Cliente> findAll();

  @Override
  long count();

  @Override
  void deleteById(@NonNull String s);

  @Override
  void delete(@NonNull Cliente entity);
}
