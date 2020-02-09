package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.mail.dimaushenko.repository.ConnectionPool;
import ru.mail.dimaushenko.repository.DatabaseRepository;
import ru.mail.dimaushenko.repository.impl.ConnectionPoolMysqlImpl;
import ru.mail.dimaushenko.repository.impl.DatabaseRepositoryImpl;
import ru.mail.dimaushenko.service.DatabaseService;

import static ru.mail.dimaushenko.constants.SqlConstants.SQL_DATABASE_NAME;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_DATABASE_CREATE;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;

public class DatabaseServiceImpl implements DatabaseService {

    private static DatabaseService instance = null;

    private final ConnectionPool connectionPool;
    private final DatabaseRepository repository;
    private final PropertyUtil propertyUtil;
    private final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private DatabaseServiceImpl() {
        connectionPool = ConnectionPoolMysqlImpl.getInstance();
        repository = DatabaseRepositoryImpl.getInstance();
        propertyUtil = PropertyUtilConstantsImpl.getInstance();
    }

    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean isDatabaseFound() {
        try ( Connection connection = connectionPool.getConnection()) {
            try {
                return repository.isDatabaseFound(connection, propertyUtil.getProperty(SQL_DATABASE_NAME));
            } catch (SQLException ex) {
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public void createDatabase() {
        try ( Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                repository.executeQuery(connection, propertyUtil.getProperty(SQL_REQUEST_DATABASE_CREATE));
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
