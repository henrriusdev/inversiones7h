package com.helisha.inversiones7h.services;

import com.helisha.inversiones7h.entities.Venta;

import java.util.List;

public interface IVentaService {
  <S extends Venta> void save(S entity);

  Venta findById(Long aLong);

  List<Venta> findAll();

  long count();

  void deleteById(Long aLong);

  void delete(Venta entity);
}
