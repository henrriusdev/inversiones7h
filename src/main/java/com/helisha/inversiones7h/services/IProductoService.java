package com.helisha.inversiones7h.services;

import com.helisha.inversiones7h.entities.Producto;

import java.util.List;

public interface IProductoService {

  void save(Producto producto);

  Producto findById(String id);
  

  List<Producto> findAll();

  long count();

  void delete(Producto producto);

  void deleteById(String id);
}
