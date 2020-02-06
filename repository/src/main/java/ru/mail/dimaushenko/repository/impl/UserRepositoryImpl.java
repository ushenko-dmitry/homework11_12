package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.mail.dimaushenko.repository.UserRepository;
import ru.mail.dimaushenko.repository.model.Role;
import ru.mail.dimaushenko.repository.model.User;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;

import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_ROLE_ID;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_USER_CREATED_BY;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_USER_ID;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_USER_PASSWORD;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_USER_USERNAME;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_TABLE_USER_INSERT;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_TABLE_USER_SELECT_ALL;

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
        try ( PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_TABLE_USER_INSERT))) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setDate(3, user.getCreatedBy());
            statement.setInt(4, user.getRole().getId());
            statement.execute();
        }
    }

    @Override
    public List<User> getAll(Connection connection) throws SQLException {
        List<User> userGroups = new ArrayList();
        try ( PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_TABLE_USER_SELECT_ALL))) {
            try ( ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    userGroups.add(getUser(result));
                }
            }
        }
        return userGroups;
    }

    @Override
    public String getPasswordByUsername(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public boolean isEntityFoundById(Connection connection, Integer id) throws SQLException {
//        try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_IS_USER_FOUND_BY_ID))) {
//            statement.setInt(1, id);
//            try (ResultSet result = statement.executeQuery()) {
//                if (result.next()) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
    private User getUser(ResultSet result) throws SQLException {
        User user = new User();

        user.setId(result.getInt(propertyUtil.getProperty(SQL_COLUMN_TABLE_USER_ID)));
        user.setUsername(result.getString(propertyUtil.getProperty(SQL_COLUMN_TABLE_USER_USERNAME)));
        user.setPassword(result.getString(propertyUtil.getProperty(SQL_COLUMN_TABLE_USER_PASSWORD)));
        user.setCreatedBy(result.getDate(propertyUtil.getProperty(SQL_COLUMN_TABLE_USER_CREATED_BY)));
        
        Role role = new Role();
        
        role.setId(result.getInt(propertyUtil.getProperty(SQL_COLUMN_TABLE_ROLE_ID)));
        return user;
    }

}
