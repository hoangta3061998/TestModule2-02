package com.codegym.repository;

import java.util.List;

public interface GeneralRepository<T> {
    List<T> findAll();

    void add(T t);

    void update(int id, T t);

    void delete(int id);

    T findByName(String name);
    T findById(int id);
}
