package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_DATABASE;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_DATABASE_SHOW;
import ru.mail.dimaushenko.repository.DatabaseRepository;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;

public class DatabaseRepositoryImpl implements DatabaseRepository{

    private static DatabaseRepository instance = null;

    private final PropertyUtil propertyUtil;

    private DatabaseRepositoryImpl() {
        propertyUtil = PropertyUtilConstantsImpl.getInstance();
    }

    public static DatabaseRepository getInstance() {
        if (instance == null) {
            instance = new DatabaseRepositoryImpl();
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
    public boolean isDatabaseFound(Connection connection, String databaseName) throws SQLException {
        try ( PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_DATABASE_SHOW))) {
            try ( ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    String columnName = propertyUtil.getProperty(SQL_COLUMN_DATABASE);
                    if (result.getString(columnName).equals(databaseName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
}
