package ru.mail.dimaushenko.controller.listner;

import java.lang.invoke.MethodHandles;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static ru.mail.dimaushenko.controller.constants.LogConstants.LOG_DATABASE_CREATED;
import static ru.mail.dimaushenko.controller.constants.LogConstants.LOG_TABLE_ROLE_CREATED;
import static ru.mail.dimaushenko.controller.constants.LogConstants.LOG_TABLE_ROLE_REMOVED;
import static ru.mail.dimaushenko.controller.constants.LogConstants.LOG_TABLE_USER_CREATED;
import static ru.mail.dimaushenko.controller.constants.LogConstants.LOG_TABLE_USER_REMOVED;
import ru.mail.dimaushenko.service.DatabaseService;
import ru.mail.dimaushenko.service.TableService;
import ru.mail.dimaushenko.service.impl.DatabaseServiceImpl;
import ru.mail.dimaushenko.service.impl.RoleTableServiceImpl;
import ru.mail.dimaushenko.service.impl.UserTableServiceImpl;

public class ContextListner implements ServletContextListener {

    private final TableService userTableService = UserTableServiceImpl.getInstance();
    private final TableService roleTableService = RoleTableServiceImpl.getInstance();
    private final DatabaseService databaseService = DatabaseServiceImpl.getInstance();

    private final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("contextInitialized");
        
        if (!databaseService.isDatabaseFound()){
            createDatabase();
            createTables();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed");

        removeTables();

        createTables();
    }

    private void removeTables() {
        boolean isUserTableHave = false;
        if (userTableService.isTableFound()) {
            isUserTableHave = true;
            if (userTableService.removeTable()) {
                isUserTableHave = false;
                logger.info(LOG_TABLE_USER_REMOVED);
            }
        }
        if (!isUserTableHave) {
            if (roleTableService.isTableFound()) {
                if (roleTableService.removeTable()) {
                    logger.info(LOG_TABLE_ROLE_REMOVED);
                }
            }
        }
    }

    private void createTables() {
        boolean isRoleTableCreated = false;

        if (!roleTableService.isTableFound()) {
            if (roleTableService.createTable()) {
                isRoleTableCreated = true;
                logger.info(LOG_TABLE_ROLE_CREATED);
            }
        }

        if (isRoleTableCreated) {
            if (!userTableService.isTableFound()) {
                if (userTableService.createTable()) {
                    logger.info(LOG_TABLE_USER_CREATED);
                }
            }
        }
    }
    
    private void createDatabase() {
        databaseService.createDatabase();
        logger.info(LOG_DATABASE_CREATED);
    }

}
