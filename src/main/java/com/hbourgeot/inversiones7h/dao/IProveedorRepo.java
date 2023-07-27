package com.hbourgeot.inversiones7h.dao;

import com.hbourgeot.inversiones7h.entities.Producto;
import com.hbourgeot.inversiones7h.entities.Proveedor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProveedorRepo extends CrudRepository<Proveedor, Long> {
  @Override
  <S extends Proveedor> S save(S entity);

  @Override
  <S extends Proveedor> Iterable<S> saveAll(Iterable<S> entities);

  @Override
  Optional<Proveedor> findById(Long aLong);

  // Método para buscar un producto por su nombre
  Optional<Proveedor> findByNombre(String nombre);

  @Override
  boolean existsById(Long aLong);

  @Override
  Iterable<Proveedor> findAll();

  @Override
  Iterable<Proveedor> findAllById(Iterable<Long> longs);

  @Override
  long count();

  @Override
  void deleteById(Long aLong);

  @Override
  void delete(Proveedor entity);

  @Override
  void deleteAllById(Iterable<? extends Long> longs);

  @Override
  void deleteAll(Iterable<? extends Proveedor> entities);

  @Override
  void deleteAll();
}
