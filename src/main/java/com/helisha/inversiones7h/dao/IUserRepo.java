package com.helisha.inversiones7h.dao;

import com.helisha.inversiones7h.entities.User;

import org.springframework.data.repository.CrudRepository;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepo extends CrudRepository<User, String> {
  @Override
    @NonNull
  <S extends User> S save(@NonNull S entity);

  @Override
    @NonNull
  <S extends User> Iterable<S> saveAll(@NonNull Iterable<S> entities);

  @Override
  @NonNull
  Optional<User> findById(@NonNull String s);

  @Override
  boolean existsById(@NonNull String s);

  @Override
  @NonNull
  Iterable<User> findAll();

  @Override
  @NonNull
  Iterable<User> findAllById(@NonNull Iterable<String> strings);

  @Override
  long count();

  @Override
  void deleteById(@NonNull String s);

  @Override
  void delete(@NonNull User entity);

  @Override
  void deleteAllById(@NonNull Iterable<? extends String> strings);

  @Override
  void deleteAll(@NonNull Iterable<? extends User> entities);

  @Override
  void deleteAll();
}