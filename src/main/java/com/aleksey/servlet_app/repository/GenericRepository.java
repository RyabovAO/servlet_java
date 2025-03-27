package com.aleksey.servlet_app.repository;

import java.util.List;

public interface GenericRepository<T, ID>{

    T create(T t);
    T readById(ID id);
    List<T> readAll();
    T update(T t);
    void delete(ID id);
}
