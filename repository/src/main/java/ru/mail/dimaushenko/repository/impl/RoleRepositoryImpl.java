package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_ROLE_DESCRIPTION;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_ROLE_ID;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_COLUMN_TABLE_ROLE_NAME;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_TABLE_ROLE_INSERT;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_TABLE_ROLE_SELECT_ALL;

import ru.mail.dimaushenko.repository.RoleRepository;
import ru.mail.dimaushenko.repository.model.Role;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;

public class RoleRepositoryImpl extends GeneralRepositoryImpl<Role> implements RoleRepository {

    private static RoleRepository instance = null;

    private final PropertyUtil propertyUtil;

    private RoleRepositoryImpl() {
        propertyUtil = PropertyUtilConstantsImpl.getInstance();
    }

    public static RoleRepository getInstance() {
        if (instance == null) {
            instance = new RoleRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void addEntity(Connection connection, Role role) throws SQLException {
        try ( PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_TABLE_ROLE_INSERT))) {
            statement.setString(1, role.getName());
            statement.setString(2, role.getDescription());
            statement.execute();
        }
    }

    @Override
    public List<Role> getAll(Connection connection) throws SQLException {
        List<Role> roles = new ArrayList();
        try ( PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_TABLE_ROLE_SELECT_ALL))) {
            try ( ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    roles.add(getRole(result));
                }
            }
        }
        return roles;
    }

    private Role getRole(ResultSet result) throws SQLException {
        Role role = new Role();

        role.setId(result.getInt(propertyUtil.getProperty(SQL_COLUMN_TABLE_ROLE_ID)));
        role.setName(result.getString(propertyUtil.getProperty(SQL_COLUMN_TABLE_ROLE_NAME)));
        role.setDescription(result.getString(propertyUtil.getProperty(SQL_COLUMN_TABLE_ROLE_DESCRIPTION)));

        return role;
    }

}
