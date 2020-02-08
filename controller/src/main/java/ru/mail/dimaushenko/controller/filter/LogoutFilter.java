package ru.mail.dimaushenko.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static ru.mail.dimaushenko.constants.AttributeConstants.SESSION_ATTRIBUTE_USER;

public class LogoutFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(SESSION_ATTRIBUTE_USER, null);
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
    }

}
