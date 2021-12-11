package edu.goncharova.command;

import edu.goncharova.exceptions.DAOException;
import edu.goncharova.services.RideOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RideOrderCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(RideOrderCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RideOrderService rideOrderService = RideOrderService.getRideOrderService();
        try {
            request.setAttribute("taxitypes", rideOrderService.getAllTaxiTypes());
            request.getRequestDispatcher("ride.jsp").forward(request, response);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}

