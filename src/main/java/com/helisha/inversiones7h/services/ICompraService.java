package com.helisha.inversiones7h.services;

import com.helisha.inversiones7h.entities.Compra;

import java.util.List;

public interface ICompraService {
  void save(Compra entity);

  Compra findById(Long aLong);

  List<Compra> findAll();

  long count();

  void deleteById(Long aLong);

  void delete(Compra entity);
}
