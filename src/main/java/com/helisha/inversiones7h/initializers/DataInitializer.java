package com.helisha.inversiones7h.initializers;

import com.helisha.inversiones7h.entities.User;
import com.helisha.inversiones7h.services.IUserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

  @Autowired
  public IUserService uService;

  @PostConstruct
  public void initialize() {
    if(uService.count() == 0) createDefaultUsers();
  }

  private void createDefaultUsers() {
      User user1 = new User();
      user1.setUsuario("cajero");
      user1.setClave("1234");
      user1.setNombre("Cajero");
      user1.setApellido("Cajero");
      user1.setRol(User.Rol.CAJA);
      uService.save(user1);

      User user2 = new User();
      user2.setUsuario("admin");
      user2.setClave("admin");
      user2.setNombre("Admin");
      user2.setApellido("istrador");
      user2.setRol(User.Rol.ADMIN);
      uService.save(user2);
  }
}