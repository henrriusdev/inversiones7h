package com.helisha.inversiones7h.services;

import com.helisha.inversiones7h.dao.IProductoRepo;
import com.helisha.inversiones7h.entities.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService implements IProductoService {

  @Autowired
  private IProductoRepo productoRepo;

  public Producto findByNombre(String nombre) {
      Optional<Producto> productoOptional = productoRepo.findByNombre(nombre);
      return productoOptional.orElse(null); 
  }
  
  public Producto findByCodigo(String codigo) {
      Optional<Producto> productoOptional = productoRepo.findByCodigo(codigo);
      return productoOptional.orElse(null); 
  }


  @Autowired
  private IProductoRepo repo;

  @Override
  public void save(Producto producto) {
    repo.save(producto);
  }

  @Override
  public Producto findById(String id) {
    return repo.findById(id).orElse(null);
  }

  @Override
  public List<Producto> findAll() {
    return (List<Producto>) repo.findAll();
  }

  @Override
  public long count() {
    return repo.count();
  }

  @Override
  public void delete(Producto producto) {
    repo.delete(producto);
  }

  @Override
  public void deleteById(String id) {
    repo.deleteById(id);
  }


}
