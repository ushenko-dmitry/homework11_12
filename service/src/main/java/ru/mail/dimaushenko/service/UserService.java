package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.service.model.AddUserDTO;
import ru.mail.dimaushenko.service.model.GetUserDTO;
import ru.mail.dimaushenko.service.model.UserDTO;

public interface UserService {

    void addUser(AddUserDTO addUserDTO);

    List<UserDTO> getAllUsers();

    boolean isUserFoundByUsername(String username);
    
    UserDTO getUserAuthenticated(GetUserDTO getUserDTO);

}
