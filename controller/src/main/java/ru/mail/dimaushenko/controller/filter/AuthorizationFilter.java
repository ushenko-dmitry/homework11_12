package ru.mail.dimaushenko.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static ru.mail.dimaushenko.constants.AttributeConstants.SESSION_ATTRIBUTE_USER;
import static ru.mail.dimaushenko.constants.Pages.REQUEST_LOGIN;
import static ru.mail.dimaushenko.constants.Pages.REQUEST_ROLES;
import static ru.mail.dimaushenko.constants.Pages.REQUEST_USERS;
import static ru.mail.dimaushenko.constants.Pages.URI_LOGIN;
import static ru.mail.dimaushenko.constants.Pages.URI_ROLES;
import static ru.mail.dimaushenko.constants.Pages.URI_SIGNIN;
import static ru.mail.dimaushenko.constants.Pages.URI_USERS;
import ru.mail.dimaushenko.service.model.SessionUserDTO;

public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        SessionUserDTO userDTO = (SessionUserDTO) session.getAttribute(SESSION_ATTRIBUTE_USER);
        if (userDTO == null) {
            if (request.getRequestURI().equals(URI_LOGIN)) {
                chain.doFilter(req, resp);
                return;
            }
            if (request.getRequestURI().equals(URI_SIGNIN)) {
                chain.doFilter(req, resp);
                return;
            }

            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(REQUEST_LOGIN);
            dispatcher.forward(req, resp);
        } else {

            switch (userDTO.getRoleName()) {
                case "ADMIN":
                    if (request.getRequestURI().equals(URI_ROLES)) {
                        chain.doFilter(req, resp);
                    } else {
                        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(REQUEST_ROLES);
                        dispatcher.forward(req, resp);
                    }
                    break;
                case "USER":
                    if (request.getRequestURI().equals(URI_USERS)) {
                        chain.doFilter(req, resp);
                    } else {
                        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(REQUEST_USERS);
                        dispatcher.forward(req, resp);
                    }
                    break;
            }
        }
    }

}
