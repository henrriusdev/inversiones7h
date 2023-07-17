package com.hbourgeot.inversiones7h.dao;

import com.hbourgeot.inversiones7h.entities.Venta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IVentaRepo extends CrudRepository<Venta, Long> {
  @Override
  <S extends Venta> S save(S entity);

  @Override
  Optional<Venta> findById(Long aLong);

  @Override
  Iterable<Venta> findAll();

  @Override
  long count();

  @Override
  void deleteById(Long aLong);

  @Override
  void delete(Venta entity);
}
