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
import ru.mail.dimaushenko.repository.UserRepository;
import ru.mail.dimaushenko.repository.impl.ConnectionPoolImpl;
import ru.mail.dimaushenko.repository.impl.UserRepositoryImpl;
import ru.mail.dimaushenko.repository.model.Role;
import ru.mail.dimaushenko.repository.model.User;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.model.AddUserDTO;
import ru.mail.dimaushenko.service.model.GetUserDTO;
import ru.mail.dimaushenko.service.model.UserDTO;

public class UserServiceImpl implements UserService {

    private static UserService instance = null;

    private final ConnectionPool connectionPool;
    private final UserRepository userRepository;
    private final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private UserServiceImpl() {
        connectionPool = ConnectionPoolImpl.getInstance();
        userRepository = UserRepositoryImpl.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public void addUser(AddUserDTO addUserDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertAddUserDTOToUser(addUserDTO);
                userRepository.addEntity(connection, user);
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> usersDTO = new ArrayList();

        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> user = userRepository.getAll(connection);
                usersDTO = convertUsersToUsersDTO(user);
                connection.commit();
            } catch (SQLException ex) {
                logger.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }

        return usersDTO;
    }

    @Override
    public boolean isUserFoundByUsername(String username) {
        try (Connection connection = connectionPool.getConnection()) {
            try {
                return userRepository.isUserFoundByUsername(connection, username);
            } catch (SQLException ex) {
                logger.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public UserDTO getUserAuthenticated(GetUserDTO getUserDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertGetUserDTOToUser(getUserDTO);
                user = userRepository.getUserAuthenticated(connection, user);
                UserDTO userDTO = null;
                if (user != null) {
                    userDTO = convertUserToUserDTO(user);
                }
                connection.commit();
                return userDTO;
            } catch (SQLException ex) {
                logger.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    private User convertAddUserDTOToUser(AddUserDTO addUserDTO) {
        User user = new User();

        user.setUsername(addUserDTO.getUsername());
        user.setPassword(addUserDTO.getPassword());

        Role role = new Role();
        role.setId(addUserDTO.getRole());
        user.setRole(role);

        return user;
    }

    private UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setCreatedBy(user.getCreatedBy());
        userDTO.setRoleId(user.getRole().getId());
        userDTO.setRoleName(user.getRole().getName());
        userDTO.setRoleDescription(user.getRole().getDescription());

        return userDTO;
    }

    private List<UserDTO> convertUsersToUsersDTO(List<User> users) {
        List<UserDTO> usersDTO = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = convertUserToUserDTO(user);
            usersDTO.add(userDTO);
        }

        return usersDTO;
    }

    private User convertGetUserDTOToUser(GetUserDTO userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        return user;
    }

}
