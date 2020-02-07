package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_TABLE_ROLE_CREATE;
import static ru.mail.dimaushenko.constants.SqlConstants.SQL_REQUEST_TABLE_ROLE_DROP;

import ru.mail.dimaushenko.repository.TableRepository;
import ru.mail.dimaushenko.repository.impl.ConnectionPoolImpl;
import ru.mail.dimaushenko.repository.impl.TableRepositoryImpl;
import ru.mail.dimaushenko.service.TableService;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;

import static ru.mail.dimaushenko.constants.SqlConstants.SQL_TABLE_NAME_ROLE;

public class RoleTableServiceImpl implements TableService {

    private static TableService instance = null;

    private final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final PropertyUtil propertyUtil;
    private final TableRepository tableRepository;

    private RoleTableServiceImpl() {
        propertyUtil = PropertyUtilConstantsImpl.getInstance();
        tableRepository = TableRepositoryImpl.getInstance();
    }

    public static TableService getInstance() {
        if (instance == null) {
            instance = new RoleTableServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean createTable() {
        try ( Connection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try {
                String query = propertyUtil.getProperty(SQL_REQUEST_TABLE_ROLE_CREATE);
                tableRepository.executeQuery(connection, query);
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
                String query = propertyUtil.getProperty(SQL_REQUEST_TABLE_ROLE_DROP);
                tableRepository.executeQuery(connection, query);
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
                String tableName = propertyUtil.getProperty(SQL_TABLE_NAME_ROLE);
                isTableFound = tableRepository.isTableFound(connection, tableName);
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
