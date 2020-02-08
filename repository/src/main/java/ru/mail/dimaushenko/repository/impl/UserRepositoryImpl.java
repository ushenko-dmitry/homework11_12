package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_ROLE_DESCRIPTION;

import ru.mail.dimaushenko.repository.UserRepository;
import ru.mail.dimaushenko.repository.model.Role;
import ru.mail.dimaushenko.repository.model.User;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;

import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_ROLE_ID;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_ROLE_NAME;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_USER_CREATED_BY;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_USER_ID;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_USER_PASSWORD;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_USER_USERNAME;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_TABLE_USER_INSERT;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_TABLE_USER_SELECT_ALL;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_TABLE_USER_SELECT_BY_USERNAME;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_TABLE_USER_SELECT_BY_USERNAME_PASSWORD;

public class UserRepositoryImpl extends GeneralRepositoryImpl<User> implements UserRepository {

    private static UserRepository instance = null;

    private final PropertyUtil propertyUtil;

    private UserRepositoryImpl() {
        propertyUtil = PropertyUtilConstantsImpl.getInstance();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void addEntity(Connection connection, User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_TABLE_USER_INSERT))) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getId());
            statement.execute();
        }
    }

    @Override
    public List<User> getAll(Connection connection) throws SQLException {
        List<User> users = new ArrayList();
        try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_TABLE_USER_SELECT_ALL))) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    users.add(getUser(result));
                }
            }
        }
        return users;
    }

    @Override
    public User getUserAuthenticated(Connection connection, User user) throws SQLException{
        try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_TABLE_USER_SELECT_BY_USERNAME_PASSWORD))) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return getUser(result);
                }
            }
        }
        return null;
    }

    @Override
    public boolean isUserFoundByUsername(Connection connection, String username) throws SQLException {
        try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_TABLE_USER_SELECT_BY_USERNAME))) {
            statement.setString(1, username);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private User getUser(ResultSet result) throws SQLException {
        User user = new User();

        user.setId(result.getInt(propertyUtil.getProperty(SQL_COLUMN_TABLE_USER_ID)));
        user.setUsername(result.getString(propertyUtil.getProperty(SQL_COLUMN_TABLE_USER_USERNAME)));
        user.setPassword(result.getString(propertyUtil.getProperty(SQL_COLUMN_TABLE_USER_PASSWORD)));
        user.setCreatedBy(result.getDate(propertyUtil.getProperty(SQL_COLUMN_TABLE_USER_CREATED_BY)));

        Role role = new Role();
        role.setId(result.getInt(propertyUtil.getProperty(SQL_COLUMN_TABLE_ROLE_ID)));
        role.setName(result.getString(propertyUtil.getProperty(SQL_COLUMN_TABLE_ROLE_NAME)));
        role.setDescription(result.getString(propertyUtil.getProperty(SQL_COLUMN_TABLE_ROLE_DESCRIPTION)));
        user.setRole(role);
        return user;
    }

}
