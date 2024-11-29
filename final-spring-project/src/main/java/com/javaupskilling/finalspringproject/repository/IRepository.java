package com.javaupskilling.finalspringproject.repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {

    List<T> getAll();

    Optional<T> getById(Long id);

    void create(T t) throws RuntimeException;

    void update(Long id, T t) throws RuntimeException;

    void delete(Long id);

}
