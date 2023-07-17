package com.hbourgeot.inversiones7h.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "proveedores")
public class Proveedor implements Serializable {

  public enum Categoria {
    Polo,
    Franela,
    Pantalon,
    Zapatos,
    Accesorios,
    Camisas
  }
  private static final long serialVersionUID = -4509451998659894417L; // codigo de la interfaz

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 150)
  private String nombre;

  @Column(nullable = false)
  private String ubicacion;

  @Column(nullable = false)
  private List<Categoria> categoria;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }

  public List<Categoria> getCategoria() {
    return categoria;
  }

  public void setCategoria(List<Categoria> categoria) {
    this.categoria = categoria;
  }
}
