package com.hbourgeot.inversiones7h.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Cliente cliente;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public List<Producto> getProductos() {
    return productos;
  }

  public void setProductos(List<Producto> productos) {
    this.productos = productos;
  }

  public BigDecimal getMontoTotal() {
    return montoTotal;
  }

  public void setMontoTotal(BigDecimal montoTotal) {
    this.montoTotal = montoTotal;
  }

  @ManyToMany
  private List<Producto> productos;

  @Column(nullable = false, precision = 7, scale = 2)
  private BigDecimal montoTotal;
}
