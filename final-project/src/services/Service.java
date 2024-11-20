package services;

import java.util.List;

public interface Service<T> {
    List<T> getAll();
    T getById(int id);
    void add(T t);
    void update(int id, T t);
    void delete(int id);
}