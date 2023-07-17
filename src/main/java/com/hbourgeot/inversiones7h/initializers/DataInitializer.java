package com.hbourgeot.inversiones7h.initializers;

import com.hbourgeot.inversiones7h.entities.User;
import com.hbourgeot.inversiones7h.services.IUserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

  @Autowired
  public IUserService uService;

  @PostConstruct
  public void initialize() {
    createDefaultUsers();
  }

  private void createDefaultUsers() {
    if (uService.count() == 0) {
      User user1 = new User();
      user1.setUsuario("estudiandev");
      user1.setClave("1234");
      user1.setNombre("Henrry");
      user1.setApellido("Bourgeot");
      user1.setRol(User.Rol.CAJA);
      uService.save(user1);

      User user2 = new User();
      user2.setUsuario("sharon");
      user2.setClave("AmoAngular18");
      user2.setNombre("Sharon");
      user2.setApellido("Gamboa");
      user2.setRol(User.Rol.ADMIN);
      uService.save(user2);
    }
  }
}