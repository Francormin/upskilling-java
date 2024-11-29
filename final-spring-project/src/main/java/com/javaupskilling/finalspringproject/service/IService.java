package com.javaupskilling.finalspringproject.service;

import com.javaupskilling.finalspringproject.exception.ExpenseNotFoundException;

import java.util.List;

public interface IService<T> {

    List<T> getAll();

    T getById(Long id) throws ExpenseNotFoundException;

    void create(T t) throws RuntimeException;

    void update(Long id, T t) throws RuntimeException;

    void delete(Long id) throws ExpenseNotFoundException;

}
