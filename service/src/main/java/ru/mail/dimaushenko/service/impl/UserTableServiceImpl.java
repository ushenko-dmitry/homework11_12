package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_TABLE_USER_CREATE;

import ru.mail.dimaushenko.repository.impl.ConnectionPoolImpl;
import ru.mail.dimaushenko.service.TableService;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;
import ru.mail.dimaushenko.repository.TableRepository;
import ru.mail.dimaushenko.repository.impl.TableRepositoryImpl;

import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_TABLE_USER_DROP;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_TABLE_NAME_USER;

public class UserTableServiceImpl implements TableService {

    private static TableService instance = null;

    private final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final PropertyUtil propertyUtil;
    private final TableRepository tableRepository;

    private UserTableServiceImpl() {
        propertyUtil = PropertyUtilConstantsImpl.getInstance();
        tableRepository = TableRepositoryImpl.getInstance();
    }

    public static TableService getInstance() {
        if (instance == null) {
            instance = new UserTableServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean createTable() {
        try ( Connection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try {
                tableRepository.executeQuery(connection, propertyUtil.getProperty(SQL_REQUEST_TABLE_USER_CREATE));
                connection.commit();
                return true;
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
    public boolean removeTable() {
        try ( Connection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try {
                tableRepository.executeQuery(connection, propertyUtil.getProperty(SQL_REQUEST_TABLE_USER_DROP));
                connection.commit();
                return true;
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
    public boolean isTableFound() {
        boolean isTableFound = false;
        try ( Connection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try {
                isTableFound = tableRepository.isTableFound(connection, propertyUtil.getProperty(SQL_TABLE_NAME_USER));
                connection.commit();
            } catch (SQLException ex) {
                logger.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return isTableFound;
    }

}
