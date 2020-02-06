package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GeneralRepository<T> {

    void addEntity(Connection connection, T t) throws SQLException;

    List<T> getAll(Connection connection) throws SQLException;

}
