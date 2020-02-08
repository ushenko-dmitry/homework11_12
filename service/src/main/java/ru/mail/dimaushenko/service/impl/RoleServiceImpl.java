package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import ru.mail.dimaushenko.service.model.AddRoleDTO;

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
    public void addRole(AddRoleDTO addRoleDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Role role = convertAddRoleDTOToRole(addRoleDTO);
                roleRepository.addEntity(connection, role);
                connection.commit();
                System.out.println("\n\nCOMMIT\n\n");
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
                System.out.println("\n\nROLLBACK\n\n");
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<RoleDTO> rolesDTO = new ArrayList();

        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Role> roles = roleRepository.getAll(connection);
                rolesDTO = convertRolesToRolesDTO(roles);
                connection.commit();
            } catch (SQLException ex) {
                logger.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }

        return rolesDTO;
    }

    private Role convertAddRoleDTOToRole(AddRoleDTO roleDTO) {
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

    private List<RoleDTO> convertRolesToRolesDTO(List<Role> roles) {
        List<RoleDTO> rolesDTO = new ArrayList<>();

        for (Role role : roles) {
            RoleDTO roleDTO = convertRoleToRoleDTO(role);
            rolesDTO.add(roleDTO);
        }

        return rolesDTO;
    }

}
