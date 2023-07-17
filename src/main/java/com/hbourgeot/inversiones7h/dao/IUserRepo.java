package com.hbourgeot.inversiones7h.dao;

import com.hbourgeot.inversiones7h.entities.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface IUserRepo extends CrudRepository<User, String> {
  @Override
  <S extends User> S save(S entity);

  @Override
  <S extends User> Iterable<S> saveAll(Iterable<S> entities);

  @Override
  Optional<User> findById(String s);

  @Override
  boolean existsById(String s);

  @Override
  Iterable<User> findAll();

  @Override
  Iterable<User> findAllById(Iterable<String> strings);

  @Override
  long count();

  @Override
  void deleteById(String s);

  @Override
  void delete(User entity);

  @Override
  void deleteAllById(Iterable<? extends String> strings);

  @Override
  void deleteAll(Iterable<? extends User> entities);

  @Override
  void deleteAll();
}