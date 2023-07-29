package com.helisha.inversiones7h.dao;

import com.helisha.inversiones7h.entities.Venta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IVentaRepo extends CrudRepository<Venta, Long> {
  @Override
    @NonNull
  <S extends Venta> S save(@NonNull S entity);

  @Override
  @NonNull
  Optional<Venta> findById(@NonNull Long aLong);

  @Override
  @NonNull
  Iterable<Venta> findAll();

  @Override
  long count();

  @Override
  void deleteById(@NonNull Long aLong);

  @Override
  void delete(@NonNull Venta entity);
}
