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

public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
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
            return;
        }
        chain.doFilter(req, resp);
    }

}
