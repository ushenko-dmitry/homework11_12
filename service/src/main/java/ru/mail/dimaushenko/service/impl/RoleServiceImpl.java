package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import ru.mail.dimaushenko.repository.ConnectionPool;
import ru.mail.dimaushenko.repository.RoleRepository;
import ru.mail.dimaushenko.repository.impl.ConnectionPoolImpl;
import ru.mail.dimaushenko.repository.impl.RoleRepositoryImpl;
import ru.mail.dimaushenko.repository.model.Role;
import ru.mail.dimaushenko.service.RoleService;
import ru.mail.dimaushenko.service.model.RoleDTO;
import ru.mail.dimaushenko.service.model.addRoleDTO;

public class RoleServiceImpl implements RoleService {

    private static RoleService instance = null;

    private final ConnectionPool connectionPool;
    private final RoleRepository roleRepository;
    private final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private RoleServiceImpl() {
        connectionPool = ConnectionPoolImpl.getInstance();
        roleRepository = RoleRepositoryImpl.getInstance();
    }

    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleServiceImpl();
        }
        return instance;
    }

    @Override
    public void addRole(addRoleDTO addRoleDTO) {
        try (Connection connection = connectionPool.getConnection()){
            connection.setAutoCommit(false);
            try {
                Role role = convertAddRoleDTOToRole(addRoleDTO);
                roleRepository.addEntity(connection, role);
            }catch (SQLException ex){
                connection.rollback();
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(RoleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Role convertAddRoleDTOToRole(addRoleDTO roleDTO) {
        Role role = new Role();

        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());

        return role;
    }

    private RoleDTO convertRoleToRoleDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setDescription(role.getDescription());

        return roleDTO;
    }

}
