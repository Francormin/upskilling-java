package dao;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();
    T getById(int id);
    void add(T t);
    void update(int id, T t);
    void delete(int id);
}