package ru.mail.dimaushenko.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static ru.mail.dimaushenko.constants.AttributeConstants.ATTRIBUTE_NAME_ROLES;
import static ru.mail.dimaushenko.constants.Pages.PAGE_SIGNIN;
import ru.mail.dimaushenko.service.RoleService;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.impl.RoleServiceImpl;
import ru.mail.dimaushenko.service.impl.UserServiceImpl;
import ru.mail.dimaushenko.service.model.AddUserDTO;
import ru.mail.dimaushenko.service.model.RoleDTO;
import ru.mail.dimaushenko.utils.Validator;
import ru.mail.dimaushenko.utils.impl.ValidatorImpl;

public class SigninServlet extends ManagerServlet {

    private final RoleService roleService = RoleServiceImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstaice();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AddUserDTO addUserDTO = getAddUserDTO(req);
        if (addUserDTO != null) {
            userService.addUser(addUserDTO);
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            doGet(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RoleDTO> roles = roleService.getAllRoles();
        req.setAttribute(ATTRIBUTE_NAME_ROLES, roles);
        forward(PAGE_SIGNIN, req, resp);
    }

    private AddUserDTO getAddUserDTO(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String roleId_str = req.getParameter("roleId");

        boolean isValid = true;
        if (!validator.isInteger(roleId_str)) {
            isValid = false;
        }
        if (!validator.isStringLengthValid(username, 40)) {
            isValid = false;
        }
        if (!validator.isStringLengthValid(password, 40)) {
            isValid = false;
        }
        if (!validator.isStringContainsUsernameSymbols(username)) {
            isValid = false;
        }

        if (isValid) {
            AddUserDTO addUserDTO = new AddUserDTO();
            addUserDTO.setUsername(username);
            addUserDTO.setPassword(password);
            Integer roleId = Integer.parseInt(roleId_str);
            addUserDTO.setRoleId(roleId);
            return addUserDTO;
        }
        return null;
    }

}
