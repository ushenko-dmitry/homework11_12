package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.mail.dimaushenko.repository.ConnectionPool;
import ru.mail.dimaushenko.repository.UserRepository;
import ru.mail.dimaushenko.repository.impl.ConnectionPoolImpl;
import ru.mail.dimaushenko.repository.impl.UserRepositoryImpl;
import ru.mail.dimaushenko.service.UserService;

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

}
