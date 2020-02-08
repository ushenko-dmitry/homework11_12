package ru.mail.dimaushenko.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static ru.mail.dimaushenko.constants.AttributeConstants.SESSION_ATTRIBUTE_USER;
import static ru.mail.dimaushenko.constants.Pages.PAGE_ROLES;
import static ru.mail.dimaushenko.constants.Pages.PAGE_USERS;
import ru.mail.dimaushenko.service.model.SessionUserDTO;

public class ManagerServlet extends HttpServlet {

    protected void forward(String to, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(to);
        requestDispatcher.forward(request, response);
    }

    protected void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SessionUserDTO userDTO = (SessionUserDTO) session.getAttribute(SESSION_ATTRIBUTE_USER);
        RequestDispatcher requestDispatcher = null;
        switch (userDTO.getRoleName()) {
            case "ADMIN":
                requestDispatcher = getServletContext().getRequestDispatcher(PAGE_ROLES);
                break;
            case "USER":
                requestDispatcher = getServletContext().getRequestDispatcher(PAGE_USERS);
                break;
        }

        requestDispatcher.forward(request, response);
    }

}
