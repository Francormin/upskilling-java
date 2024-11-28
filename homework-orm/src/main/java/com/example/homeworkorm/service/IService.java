package com.example.homeworkorm.service;

import java.util.List;

public interface IService<T> {

    List<T> getAll();

    T getById(Long id);

    void add(T t);

    void update(Long id, T t);

    void delete(Long id);

}
