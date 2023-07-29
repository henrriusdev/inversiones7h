package com.helisha.inversiones7h.services;

import com.helisha.inversiones7h.entities.User;


import java.util.List;

public interface IUserService {

  User save(User entity);

  User findById(String userName);

  boolean existsById(String userName);

  List<User> findAll();

  long count();

  void deleteById(String userName);
}
