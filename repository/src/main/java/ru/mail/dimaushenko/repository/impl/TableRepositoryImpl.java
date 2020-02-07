package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ru.mail.dimaushenko.repository.TableRepository;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;

import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_DATABASE_TABLES;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_DATABASE_GET_TABLES;

public class TableRepositoryImpl implements TableRepository {

    private static TableRepository instance = null;

    private final PropertyUtil propertyUtil;

    private TableRepositoryImpl() {
        propertyUtil = PropertyUtilConstantsImpl.getInstance();
    }

    public static TableRepository getInstance() {
        if (instance == null) {
            instance = new TableRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void executeQuery(Connection connection, String query) throws SQLException {
        try ( PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

    @Override
    public boolean isTableFound(Connection connection, String tableName) throws SQLException {
        try ( PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_DATABASE_GET_TABLES))) {
            try ( ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    String columnName = propertyUtil.getProperty(SQL_COLUMN_DATABASE_TABLES);
                    if (result.getString(columnName).equals(tableName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
