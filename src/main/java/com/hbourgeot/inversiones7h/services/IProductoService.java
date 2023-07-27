package com.hbourgeot.inversiones7h.services;

import com.hbourgeot.inversiones7h.entities.Producto;

import java.util.List;

public interface IProductoService {

  void save(Producto producto);

  Producto findById(String id);
  

  List<Producto> findAll();

  List<Producto> findDisponibles();

  long count();

  void delete(Producto producto);

  void deleteById(String id);
}
