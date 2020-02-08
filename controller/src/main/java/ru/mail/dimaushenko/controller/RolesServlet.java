package ru.mail.dimaushenko.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static ru.mail.dimaushenko.constants.AttributeConstants.ATTRIBUTE_NAME_ROLES;
import static ru.mail.dimaushenko.constants.Pages.PAGE_ROLES;
import ru.mail.dimaushenko.service.RoleService;
import ru.mail.dimaushenko.service.impl.RoleServiceImpl;
import ru.mail.dimaushenko.service.model.RoleDTO;

public class RolesServlet extends ManagerServlet {

    private final RoleService roleService = RoleServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RoleDTO> roles = roleService.getAllRoles();

        req.setAttribute(ATTRIBUTE_NAME_ROLES, roles);

        forward(PAGE_ROLES, req, resp);
    }

}
