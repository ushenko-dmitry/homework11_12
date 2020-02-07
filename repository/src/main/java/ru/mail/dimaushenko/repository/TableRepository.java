package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface TableRepository {

    public void executeQuery(Connection connection, String query) throws SQLException;

    public boolean isTableFound(Connection connection, String tableName) throws SQLException;

}
