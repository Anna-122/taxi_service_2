package edu.goncharova.command;

import edu.goncharova.domain.User;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.services.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("currentUser") != null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null) {
            request.setAttribute("errorMessageLogin", "You should fill email");
            LOGGER.info("Empty email");
            request.getRequestDispatcher(CommandFactory.LOGIN + ".jsp").forward(request, response);
            return;
        }
        if (password == null) {
            request.setAttribute("errorMessageLogin", "You should fill password");
            LOGGER.info("Empty password");
            request.getRequestDispatcher(CommandFactory.LOGIN + ".jsp").forward(request, response);
            return;
        }
        LoginService loginService = LoginService.getLoginService();
        try {
            User user = loginService.getUser(email, password);
            if (user != null) {
                LOGGER.info("User {} logged in.", email);
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", user);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                LOGGER.info("User {} couldn't log in.", email);
                request.setAttribute("errorMessageLogin", "Login or password incorrect");
                request.getRequestDispatcher(CommandFactory.LOGIN + ".jsp").forward(request, response);
            }
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}

