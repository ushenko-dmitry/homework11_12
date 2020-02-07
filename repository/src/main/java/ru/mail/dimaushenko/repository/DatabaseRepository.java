package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseRepository {

    void executeQuery(Connection connection, String query) throws SQLException;
            
    boolean isDatabaseFound(Connection connection, String databaseName) throws SQLException;
    
}
