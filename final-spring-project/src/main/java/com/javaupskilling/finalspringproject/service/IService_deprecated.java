package com.javaupskilling.finalspringproject.service;

import com.javaupskilling.finalspringproject.exception.ExpenseNotFoundException_deprecated;

import java.util.List;

@Deprecated
public interface IService_deprecated<T> {

    List<T> getAll();

    T getById(Long id) throws ExpenseNotFoundException_deprecated;

    void create(T t) throws RuntimeException;

    void update(Long id, T t) throws RuntimeException;

    void delete(Long id) throws ExpenseNotFoundException_deprecated;

}
