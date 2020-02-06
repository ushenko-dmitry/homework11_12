package ru.mail.dimaushenko.controller.filter;

import java.io.IOException;
import java.net.http.HttpRequest;
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
            if (request.getRequestURI().equals("/homework11_12/login")){
                System.out.println("lets to login");
                chain.doFilter(req, resp);
                return;
            }
            if (request.getRequestURI().equals("/homework11_12/signin")){
                System.out.println("lets to signin");
                chain.doFilter(req, resp);
                return;
            }
            System.out.println("redirect to login");
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login");
            dispatcher.forward(req, resp);
            System.out.println("gosdfsfd");
        }else{
            System.out.println("go");
        }
        System.out.println("adads");
        chain.doFilter(req, resp);
    }

}
