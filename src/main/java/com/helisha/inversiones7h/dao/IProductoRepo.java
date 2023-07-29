package com.helisha.inversiones7h.dao;

import com.helisha.inversiones7h.entities.Producto;


import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductoRepo extends CrudRepository<Producto, String> {

  @Override
    @NonNull
  <S extends Producto> S save(@NonNull S entity);

  @Override
    @NonNull
  <S extends Producto> Iterable<S> saveAll(@NonNull Iterable<S> entities);

  // Método para buscar un producto por su nombre
  Optional<Producto> findByNombre(String nombre);

  // Método para buscar un producto por su codigo

  Optional<Producto> findByCodigo(String codigo);

  @Override
  boolean existsById(@NonNull String s);

  @Override
  @NonNull
  Iterable<Producto> findAll();

  @Override
  @NonNull
  Iterable<Producto> findAllById(@NonNull Iterable<String> strings);

  @Override
  long count();

  @Override
  void deleteById(@NonNull String s);

  @Override
  void delete(@NonNull Producto entity);

  @Override
  void deleteAllById(@NonNull Iterable<? extends String> strings);

  @Override
  void deleteAll(@NonNull Iterable<? extends Producto> entities);

  @Override
  void deleteAll();
}
