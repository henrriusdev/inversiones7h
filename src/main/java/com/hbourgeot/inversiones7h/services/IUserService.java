package com.hbourgeot.inversiones7h.services;

import com.hbourgeot.inversiones7h.entities.User;

import java.util.Optional;
import java.util.List;

public interface IUserService {

  User save(User entity);

  User findById(String userName);

  boolean existsById(String userName);

  List<User> findAll();

  long count();

  void deleteById(String userName);
}
