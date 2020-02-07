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
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpSession session = request.getSession();
//        System.out.println(request.getRequestURI());
//        if (session.getAttribute("user") == null) {
//            if (request.getRequestURI().equals("/login")) {
//                System.out.println("lets to login");
//                chain.doFilter(req, resp);
//                return;
//            }
//            if (request.getRequestURI().equals("/signin")) {
//                System.out.println("lets to signin");
//                chain.doFilter(req, resp);
//                return;
//            }
//
//            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login");
//            dispatcher.forward(req, resp);
//        }
        chain.doFilter(req, resp);
    }

}
