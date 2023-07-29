package com.helisha.inversiones7h.dao;

import com.helisha.inversiones7h.entities.Compra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICompraRepo extends CrudRepository<Compra, Long> {
  @Override
    @NonNull
  <S extends Compra> S save(@NonNull S entity);

  @Override
  @NonNull
  Optional<Compra> findById(@NonNull Long aLong);

  @Override
  @NonNull
  Iterable<Compra> findAll();

  @Override
  long count();

  @Override
  void deleteById(@NonNull Long aLong);

  @Override
  void delete(@NonNull Compra entity);
}
