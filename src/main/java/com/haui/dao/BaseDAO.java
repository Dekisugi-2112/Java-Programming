package com.haui.dao;

import java.util.List;

public interface BaseDAO<T, ID> {
    List<T> selectAll();
    T selectById(ID id);
    boolean insert(T entity);
    boolean update(T entity);
    boolean delete(ID id);
}
