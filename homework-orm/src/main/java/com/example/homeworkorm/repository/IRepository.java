package com.example.homeworkorm.repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {

    List<T> findAll();

    Optional<T> findById(Long id);

    void save(T t);

    void update(T t);

    void delete(Long id);

}
