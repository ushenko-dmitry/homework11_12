package ru.mail.dimaushenko.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.impl.UserServiceImpl;
import static ru.mail.dimaushenko.constants.ParameterConstants.PARAMETER_USERNAME;

public class LoginFilter implements Filter {

    private final String METHOD_GET = "GET";
    private final String METHOD_POST = "POST";

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        if (httpServletRequest.getMethod().equals(METHOD_GET)) {
            chain.doFilter(req, resp);
        }
        if (httpServletRequest.getMethod().equals(METHOD_POST)) {
            String username = httpServletRequest.getParameter(PARAMETER_USERNAME);
            if (userService.isUserFoundByUsername(username)) {
                chain.doFilter(req, resp);
                ((HttpServletResponse) resp).sendRedirect(httpServletRequest.getContextPath() + "/");
//                RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/");
//                dispatcher.forward(req, resp);
            }

        }
    }

}
