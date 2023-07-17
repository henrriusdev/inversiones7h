package com.hbourgeot.inversiones7h.entities;

import jakarta.persistence.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;

@Entity // definimos la clase user como Entidad
@Table(name = "users") // nombre de tabla
public class User implements Serializable { // implementamos una interfaz

  public enum Rol {
    CAJA, ADMIN
  }

  private static final long serialVersionUID = -4509451998659894417L; // codigo de la interfaz

  @Id
  @Column(nullable = false, unique = true, length = 45) // userName NO debe ser nulo, es ÚNICO y su longitud es de 45 caracteres
  private String usuario;

  @Column(nullable = false, length = 64)
  private String clave;

  @Column(nullable = false, length = 20)
  private String nombre;

  @Column(nullable = false, length = 20)
  private String apellido;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Rol rol;

  @Temporal(TemporalType.DATE)
  @Column(name = "fecha_ingreso")
  private String fechaIngreso;

  public String getFechaIngreso() {
    return fechaIngreso;
  }

  public void setFechaIngreso(String fechaIngreso) {
    this.fechaIngreso = fechaIngreso;
  }

  public Rol getRol() {
    return rol;
  }

  public void setRol(Rol rol){
    this.rol = rol;
  }

  public String getUsuario() {
    return usuario;
  }

  public String getClave(){
    return clave;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }
  public void setClave(String clave) {
    this.clave = BCrypt.hashpw(clave, BCrypt.gensalt());
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
}
