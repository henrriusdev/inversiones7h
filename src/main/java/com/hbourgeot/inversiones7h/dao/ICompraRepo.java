package com.hbourgeot.inversiones7h.dao;

import com.hbourgeot.inversiones7h.entities.Compra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICompraRepo extends CrudRepository<Compra, Long> {
  @Override
  <S extends Compra> S save(S entity);

  @Override
  Optional<Compra> findById(Long aLong);

  @Override
  Iterable<Compra> findAll();

  @Override
  long count();

  @Override
  void deleteById(Long aLong);

  @Override
  void delete(Compra entity);
}
