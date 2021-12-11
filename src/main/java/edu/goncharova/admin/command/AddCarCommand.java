package edu.goncharova.admin.command;

import edu.goncharova.admin.service.AddCarService;
import edu.goncharova.command.Command;
import edu.goncharova.command.CommandFactory;
import edu.goncharova.domain.Driver;
import edu.goncharova.domain.Taxi;
import edu.goncharova.exceptions.AddCarException;
import edu.goncharova.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCarCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(AddCarCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddCarService addCarService = AddCarService.getAddCarService();
        int userId = Integer.parseInt(request.getParameter("userid"));
        String carNumber = request.getParameter("carnumber");
        int carType = 0;
        try {
            carType = Integer.parseInt(request.getParameter("cartype"));
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage());
            request.setAttribute("errorMessage", "Car type should be a number");
            request.getRequestDispatcher(CommandFactory.ADD_CAR + ".jsp").forward(request, response);
            return;
        }
        if (carNumber == null) {
            LOGGER.info("Empty password");
            request.setAttribute("errorMessage", "You should fill car number");
            request.getRequestDispatcher(CommandFactory.ADD_CAR + ".jsp").forward(request, response);
            return;
        }
        try {
            Driver driver = addCarService.findDriverByUserId(userId);
            if (driver == null) {
                request.setAttribute("errorMessage", "First make this user a driver");
                request.getRequestDispatcher("controller?command=users&pageNumber=1").forward(request, response);
                return;
            }
            LOGGER.info("Trying to add car to driver with driverid={}", driver.getDriverId());

            Taxi taxi = new Taxi(driver.getDriverId(), carType, carNumber);
            addCarService.addCar(taxi);
            LOGGER.info("Successfully added taxi");
            request.setAttribute("errorMessage", "Car added");
            request.getRequestDispatcher("controller?command=users&pageNumber=1").forward(request, response);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        } catch (AddCarException e) {
            LOGGER.error(e.getMessage());
            request.setAttribute("errorMessage", "There is already a car with such number");
            request.getRequestDispatcher("controller?command=users&pageNumber=1").forward(request, response);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}

