package com.helisha.inversiones7h.entities;

import jakarta.persistence.*;

import java.io.Serializable;

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

  @Id
  @Column(length = 18, name = "cedula")
  private String cedulaIdentidad;

  @Column(nullable = false, length = 150)
  private String nombre;

  @Column(nullable = false, length = 150)
  private String apellido;

  @Column(nullable = false)
  private String ubicacion;

  @Column(nullable = false)
  private Categoria categoria;

  public String getCedulaIndentidad() {
    return cedulaIdentidad;
  }

  public void setCedulaIdentidad(String cedulaIdentidad) {
    this.cedulaIdentidad = cedulaIdentidad;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public String getCategoriaString() {
    return categoria.toString();
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }
}
