package com.helisha.inversiones7h.dao;

import com.helisha.inversiones7h.entities.Proveedor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProveedorRepo extends CrudRepository<Proveedor, Long> {
  @Override
    @NonNull
  <S extends Proveedor> S save(@NonNull S entity);

  // Método para buscar un proveedor por su cédula de identidad

  Optional<Proveedor> findByCedulaIdentidad(String cedula);

  @Override
    @NonNull
  <S extends Proveedor> Iterable<S> saveAll(@NonNull Iterable<S> entities);

  @Override
  @NonNull
  Optional<Proveedor> findById(@NonNull Long aLong);

  // Método para buscar un producto por su nombre
  Optional<Proveedor> findByNombre(String nombre);

  @Override
  @NonNull
  boolean existsById(@NonNull Long aLong);

  @Override
  @NonNull
  Iterable<Proveedor> findAll();

  @Override
  @NonNull
  Iterable<Proveedor> findAllById(@NonNull Iterable<Long> longs);

  @Override
  @NonNull
  long count();

  @Override
  @NonNull
  void deleteById(@NonNull Long aLong);

  @Override
  @NonNull
  void delete(@NonNull Proveedor entity);

  @Override
  @NonNull
  void deleteAllById(@NonNull Iterable<? extends Long> longs);

  @Override
  @NonNull
  void deleteAll(@NonNull Iterable<? extends Proveedor> entities);

  @Override
  @NonNull
  void deleteAll();
}
