package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import ru.mail.dimaushenko.repository.model.User;

public interface UserRepository extends GeneralRepository<User> {

    User getUserAuthenticated(Connection connection, User user) throws SQLException;
    
    boolean isUserFoundByUsername(Connection connection, String username) throws SQLException;
    
}
