package com.hbourgeot.inversiones7h.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable{

  @Id
  @Column(length = 18, name = "cedula")
  private String cedulaIdentidad;

  @Column(length = 60, nullable = false)
  private String nombre;

  @Column(length = 60, nullable = false)
  private String apellido;

  @Column(nullable = true, precision = 7, scale = 2, name = "gasto_total")
  private BigDecimal gastoTotal;

  @Column(nullable = false, length = 15)
  private String telefono;

  @Column(nullable = false)
  private String direccion;

  public String getCedulaIdentidad() {
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

  public BigDecimal getGastoTotal() {
    return gastoTotal;
  }

  public void setGastoTotal(BigDecimal gastoTotal) {
    this.gastoTotal = gastoTotal;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }
}
