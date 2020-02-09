package ru.mail.dimaushenko.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static ru.mail.dimaushenko.constants.AttributeConstants.ATTRIBUTE_NAME_ERRORS;
import static ru.mail.dimaushenko.constants.AttributeConstants.SESSION_ATTRIBUTE_USER;
import static ru.mail.dimaushenko.constants.Pages.PAGE_LOGIN;
import ru.mail.dimaushenko.service.model.GetUserDTO;
import static ru.mail.dimaushenko.constants.ParameterConstants.PARAMETER_USERNAME;
import static ru.mail.dimaushenko.constants.ParameterConstants.PARAMETER_PASSWORD;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.impl.UserServiceImpl;
import ru.mail.dimaushenko.service.model.SessionUserDTO;
import ru.mail.dimaushenko.service.model.UserDTO;

public class LoginServlet extends ManagerServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getAttribute(ATTRIBUTE_NAME_ERRORS) == null) {
            GetUserDTO getUserDTO = getuserDTO(req);
            UserDTO userDTO = userService.getUserAuthenticated(getUserDTO);

            if (userDTO != null) {
                SessionUserDTO sessionUserDTO = convertUserDTOToSessionConvertDTO(userDTO);
                HttpSession session = req.getSession();
                session.setAttribute(SESSION_ATTRIBUTE_USER, sessionUserDTO);
            }
        }else{
            doGet(req, resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward(PAGE_LOGIN, req, resp);
    }

    private GetUserDTO getuserDTO(HttpServletRequest req) {
        GetUserDTO userDTO = new GetUserDTO();

        userDTO.setUsername(req.getParameter(PARAMETER_USERNAME));
        userDTO.setPassword(req.getParameter(PARAMETER_PASSWORD));

        return userDTO;
    }

    private SessionUserDTO convertUserDTOToSessionConvertDTO(UserDTO userDTO) {
        SessionUserDTO sessionUserDTO = new SessionUserDTO();

        sessionUserDTO.setId(userDTO.getId());
        sessionUserDTO.setUsername(userDTO.getUsername());
        sessionUserDTO.setRoleId(userDTO.getRoleId());
        sessionUserDTO.setRoleName(userDTO.getRoleName());

        return sessionUserDTO;
    }

}
