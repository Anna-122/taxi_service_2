package edu.goncharova.filters;

import edu.goncharova.authentification.Authentication;
import edu.goncharova.configuration.SecurityConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SecurityFilter implements Filter {
    private final static Logger LOGGER = LogManager.getLogger(SecurityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityConfiguration securityConfiguration = SecurityConfiguration.getInstance();
        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
        HttpServletResponse httpServletResponse = ((HttpServletResponse) response);
        request.setCharacterEncoding("UTF-8");
        String command = httpServletRequest.getParameter("command");
        if (command == null) {
            String uri = httpServletRequest.getRequestURI();
            if (uri.endsWith("/")) uri = uri.substring(0, uri.length() - 1);
            command = uri.substring(uri.lastIndexOf("/") + 1);
        }

        String role = securityConfiguration.security(command);
        LOGGER.debug("Command is {}, role is {}", command, role);
        if (SecurityConfiguration.NO_ACCESS.equals(role)) {
            chain.doFilter(request, response);
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (SecurityConfiguration.ALL.equals(role)) {
            chain.doFilter(request, response);
            return;
        }

        if (SecurityConfiguration.AUTH.equals(role)) {
            if (Authentication.getInstance().isUserLoggedIn(httpServletRequest.getSession())) {
                chain.doFilter(request, response);
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            return;
        }
        if (SecurityConfiguration.ADMIN.equals(role)) {
            if (Authentication.getInstance().isAdminLoggedIn(httpServletRequest.getSession())) {
                chain.doFilter(request, response);
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            return;
        }
        httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    public void destroy() {

    }
}
