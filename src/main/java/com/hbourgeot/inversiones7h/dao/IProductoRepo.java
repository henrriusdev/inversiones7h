package com.hbourgeot.inversiones7h.dao;

import com.hbourgeot.inversiones7h.entities.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductoRepo extends CrudRepository<Producto, String> {

  @Override
  <S extends Producto> S save(S entity);

  @Override
  <S extends Producto> Iterable<S> saveAll(Iterable<S> entities);

  // Método para buscar un producto por su nombre
  Optional<Producto> findByNombre(String nombre);

  @Override
  boolean existsById(String s);

  @Override
  Iterable<Producto> findAll();

  @Override
  Iterable<Producto> findAllById(Iterable<String> strings);

  @Override
  long count();

  @Override
  void deleteById(String s);

  @Override
  void delete(Producto entity);

  @Override
  void deleteAllById(Iterable<? extends String> strings);

  @Override
  void deleteAll(Iterable<? extends Producto> entities);

  @Override
  void deleteAll();
}
