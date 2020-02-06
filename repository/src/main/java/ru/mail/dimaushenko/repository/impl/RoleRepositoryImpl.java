package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ru.mail.dimaushenko.repository.RoleRepository;
import ru.mail.dimaushenko.repository.model.Role;

public class RoleRepositoryImpl extends GeneralRepositoryImpl<Role> implements RoleRepository {

    private static RoleRepository instance = null;

    private RoleRepositoryImpl() {
    }

    public static RoleRepository getInstance() {
        if (instance == null) {
            instance = new RoleRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void addEntity(Connection connection, Role t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Role> getAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
