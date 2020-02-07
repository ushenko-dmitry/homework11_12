package ru.mail.dimaushenko.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static ru.mail.dimaushenko.controller.constants.AttributeConstants.ATTRIBUTE_NAME_USERS;
import static ru.mail.dimaushenko.controller.constants.Pages.PAGE_USERS;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.impl.UserServiceImpl;
import ru.mail.dimaushenko.service.model.UserDTO;

public class UsersServlet extends ManagerServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDTO> users = userService.getAllUsers();

        req.setAttribute(ATTRIBUTE_NAME_USERS, users);

        forward(PAGE_USERS, req, resp);
    }

}
