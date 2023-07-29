package com.helisha.inversiones7h.services;

import com.helisha.inversiones7h.dao.IUserRepo;
import com.helisha.inversiones7h.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class UserService implements IUserService{

  @Autowired
  public IUserRepo repo;

  @Override
  public User save(User entity) {
    return repo.save(entity);
  }

  @Override
  public User findById(String userName) {
    return repo.findById(userName).orElse(null);
  }

  @Override
  public boolean existsById(String userName) {
    return repo.existsById(userName);
  }

  @Override
  public List<User> findAll() {
    return (List<User>) repo.findAll();
  }

  @Override
  public long count() {
    return repo.count();
  }

  @Override
  public void deleteById(String userName) {
    repo.deleteById(userName);
  }
}
