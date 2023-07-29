package com.helisha.inversiones7h.services;

import com.helisha.inversiones7h.entities.Proveedor;

import java.util.List;

public interface IProveedorService {
  void save(Proveedor proveedor);

  Proveedor findById(Long id);

  List<Proveedor> findAll();

  long count();

  void deleteById(Long id);

  void delete(Proveedor proveedor);
}
