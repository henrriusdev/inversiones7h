package com.hbourgeot.inversiones7h.services;

import com.hbourgeot.inversiones7h.dao.IProductoRepo;
import com.hbourgeot.inversiones7h.dao.IProveedorRepo;
import com.hbourgeot.inversiones7h.entities.Cliente;
import com.hbourgeot.inversiones7h.entities.Producto;
import com.hbourgeot.inversiones7h.entities.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService implements IProveedorService{
  
  @Autowired
  private IProveedorRepo proveedorRepo;

  public Proveedor findByCedulaIdentidad(String cedula) {
      Optional<Proveedor> proveedorOptional = proveedorRepo.findByCedulaIdentidad(cedula);
      return proveedorOptional.orElse(null); 
  }

  public Proveedor findByNombre(String nombre) {
      Optional<Proveedor> proveedorOptional = proveedorRepo.findByNombre(nombre);
      return proveedorOptional.orElse(null); 
  }
  
  @Autowired
  public IProveedorRepo repo;

  @Override
  public void save(Proveedor proveedor) {
    repo.save(proveedor);
  }

  @Override
  public Proveedor findById(Long id) {
    return repo.findById(id).orElse(null);
  }

  @Override
  public List<Proveedor> findAll() {
    return (List<Proveedor>) repo.findAll();
  }

  @Override
  public long count() {
    return repo.count();
  }

  @Override
  public void deleteById(Long id) {
    repo.deleteById(id);
  }

  @Override
  public void delete(Proveedor proveedor) {
    repo.delete(proveedor);
  }
}
