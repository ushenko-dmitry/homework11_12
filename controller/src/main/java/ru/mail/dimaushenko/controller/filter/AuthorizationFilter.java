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
import static ru.mail.dimaushenko.constants.Pages.PAGE_ROLES;
import static ru.mail.dimaushenko.constants.Pages.PAGE_USERS;
import ru.mail.dimaushenko.service.model.SessionUserDTO;

public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("AuthorizationFilter start");
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println(request.getRequestURI());
        HttpSession session = request.getSession();
        SessionUserDTO userDTO = (SessionUserDTO) session.getAttribute(SESSION_ATTRIBUTE_USER);
        if (userDTO == null) {
            if (request.getRequestURI().equals("/homework11_12/login")) {
                chain.doFilter(req, resp);
                return;
            }
            if (request.getRequestURI().equals("/homework11_12/signin")) {
                chain.doFilter(req, resp);
                return;
            }

            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login");
            dispatcher.forward(req, resp);
        } else {

            switch (userDTO.getRoleName()) {
                case "ADMIN":
                    System.out.println("ADMIN");
                    if (request.getRequestURI().equals("/homework11_12/roles")) {
                        System.out.println("GO to admin");
                        chain.doFilter(req, resp);
                    } else {
                        System.out.println("FORWARD to admin");
                        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/roles");
                        dispatcher.forward(req, resp);
                    }
                    break;
                case "USER":
                    if (request.getRequestURI().equals("/homework11_12/users")) {
                        chain.doFilter(req, resp);
                    } else {
                        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/users");
                        dispatcher.forward(req, resp);
                    }
                    break;
            }
        }
    }

}
