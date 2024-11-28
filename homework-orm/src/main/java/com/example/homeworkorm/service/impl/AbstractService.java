package com.example.homeworkorm.service.impl;

import com.example.homeworkorm.exception.EntityNotFoundException;
import com.example.homeworkorm.service.IService;
import com.example.homeworkorm.util.ExceptionMessageUtil;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractService<T, R extends JpaRepository<T, Long>> implements IService<T> {

    protected final R repository;
    private final String entityName;

    public AbstractService(R repository, String entityName) {
        this.repository = repository;
        this.entityName = entityName;
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public T getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessageUtil.entityNotFound(entityName, id)
            ));
    }

    @Override
    public void add(T entity) {
        repository.save(entity);
    }

    @Override
    public void update(Long id, T entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                ExceptionMessageUtil.entityNotFound(entityName, id)
            );
        }
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                ExceptionMessageUtil.entityNotFound(entityName, id)
            );
        }
        repository.deleteById(id);
    }

}
