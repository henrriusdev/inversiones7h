package com.helisha.inversiones7h.entities;

import jakarta.persistence.*;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

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

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Producto> productos;

  @Column(nullable = false, precision = 7, scale = 2)
  private BigDecimal montoTotal;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCliente() {
    return cliente.getCedulaIdentidad() + " - " + cliente.getNombre() + " " + cliente.getApellido();
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public String getProductos(){
    StringBuilder concatenacion = new StringBuilder();

    // Recorremos la lista de productos y concatenamos el código y el nombre de cada producto
    if(productos.size() > 1) {
      for (Producto producto : productos) {
        concatenacion.append(concatenacion).append("#").append(producto.getCodigo()).append(", ");
      }
    } else{
      for (Producto producto : productos) {
        concatenacion.append(concatenacion).append("#").append(producto.getCodigo());
      }
    }
    return concatenacion.toString();
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
}
