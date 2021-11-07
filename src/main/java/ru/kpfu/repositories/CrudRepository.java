package ru.kpfu.repositories;

import java.util.List;

public interface CrudRepository<T> {
    void save(T entity);
    void update(T entity);
    void delete(T entity);

    T findById(long id);
    List<T> findAll();
}
