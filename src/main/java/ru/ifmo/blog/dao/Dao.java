package ru.ifmo.blog.dao;

import java.sql.SQLException;
import java.util.Optional;

public interface Dao<T> {
    void save(T t) throws SQLException;
    Optional<T> get(Long id) throws SQLException;
    void remove(Integer id) throws SQLException;

    boolean remove(Long id) throws SQLException;
    boolean removeAll() throws  SQLException;
}
