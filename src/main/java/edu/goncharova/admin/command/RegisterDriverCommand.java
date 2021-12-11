package edu.goncharova.admin.command;

import edu.goncharova.admin.service.RegisterDriverService;
import edu.goncharova.command.Command;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.exceptions.DriverRegistrationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterDriverCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(RegisterDriverCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RegisterDriverService registerDriverService = RegisterDriverService.getRegisterDriverService();
        int userId= Integer.parseInt(request.getParameter("userid"));
        LOGGER.info("Trying to register as driver user with userid={}",userId);
        try {
            registerDriverService.registerDriver(userId);
            LOGGER.info("Successfully registered user {}",userId);
            request.setAttribute("errorMessage","Successfully registered user as driver");
            request.getRequestDispatcher("controller?command=users&pageNumber=1").forward(request,response);
        }catch (DriverRegistrationException e){
            LOGGER.error(e.getMessage());
            request.setAttribute("errorMessage",e.getMessage());
            request.getRequestDispatcher("controller?command=users&pageNumber=1").forward(request,response);
        }
        catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    @Override
    public String toString(){
        return this.getClass().getName();
    }
}

