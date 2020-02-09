package ru.mail.dimaushenko.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static ru.mail.dimaushenko.constants.AttributeConstants.ATTRIBUTE_NAME_ERRORS;
import static ru.mail.dimaushenko.constants.AttributeConstants.ATTRIBUTE_NAME_ROLES;
import static ru.mail.dimaushenko.constants.ErrorsConstants.ERROR_KEY_PASSWORD_LENGTH;
import static ru.mail.dimaushenko.constants.ErrorsConstants.ERROR_KEY_ROLE;
import static ru.mail.dimaushenko.constants.ErrorsConstants.ERROR_KEY_USERNAME_LENGTH;
import static ru.mail.dimaushenko.constants.ErrorsConstants.ERROR_KEY_USERNAME_SYMBOLS;
import static ru.mail.dimaushenko.constants.ErrorsConstants.ERROR_VALUE_PASSWORD_LENGTH;
import static ru.mail.dimaushenko.constants.ErrorsConstants.ERROR_VALUE_ROLE;
import static ru.mail.dimaushenko.constants.ErrorsConstants.ERROR_VALUE_USERNAME_LENGTH;
import static ru.mail.dimaushenko.constants.ErrorsConstants.ERROR_VALUE_USERNAME_SYMBOLS;
import static ru.mail.dimaushenko.constants.Pages.PAGE_SIGNIN;
import static ru.mail.dimaushenko.constants.Pages.REQUEST_LOGIN;
import static ru.mail.dimaushenko.constants.ParameterConstants.PARAMETER_PASSWORD;
import static ru.mail.dimaushenko.constants.ParameterConstants.PARAMETER_ROLE_ID;
import static ru.mail.dimaushenko.constants.ParameterConstants.PARAMETER_USERNAME;
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
    private final Map<String, String> errorMessages = new HashMap<>();
    private final Integer maxStringLength = 40;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AddUserDTO addUserDTO = getAddUserDTO(req);
        req.setAttribute(ATTRIBUTE_NAME_ERRORS, errorMessages);
        if (addUserDTO != null) {
            userService.addUser(addUserDTO);
            resp.sendRedirect(req.getContextPath() + REQUEST_LOGIN);
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
        String username = req.getParameter(PARAMETER_USERNAME);
        String password = req.getParameter(PARAMETER_PASSWORD);
        String roleId_str = req.getParameter(PARAMETER_ROLE_ID);

        boolean isValid = true;
        if (!validator.isInteger(roleId_str)) {
            isValid = false;
            errorMessages.put(ERROR_KEY_ROLE, ERROR_VALUE_ROLE);
        }
        if (!validator.isStringLengthValid(username, maxStringLength)) {
            isValid = false;
            errorMessages.put(ERROR_KEY_USERNAME_LENGTH, ERROR_VALUE_USERNAME_LENGTH);
        }
        if (!validator.isStringLengthValid(password, maxStringLength)) {
            isValid = false;
            errorMessages.put(ERROR_KEY_PASSWORD_LENGTH, ERROR_VALUE_PASSWORD_LENGTH);
        }
        if (!validator.isStringContainsUsernameSymbols(username)) {
            isValid = false;
            errorMessages.put(ERROR_KEY_USERNAME_SYMBOLS, ERROR_VALUE_USERNAME_SYMBOLS);
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
