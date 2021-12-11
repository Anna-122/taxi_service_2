package edu.goncharova.command;

import edu.goncharova.domain.Client;
import edu.goncharova.domain.Driver;
import edu.goncharova.domain.Taxi;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.services.RideStatisticsService;
import edu.goncharova.services.TaxiIdentifierService;
import edu.goncharova.services.TimeCalculationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

public class RideCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(RideCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RideStatisticsService rideStatisticsService = RideStatisticsService.getRideStatisticsService();
        if ("false".equals(request.getParameter("approved"))) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        Client client = (Client) request.getSession().getAttribute("client");
        double cost = Double.parseDouble((request.getParameter("cost")));
        double distance = Double.parseDouble(request.getParameter("distance"));
        String carNumber = request.getParameter("carNumber");
        try {
            TaxiIdentifierService taxiIdentifierService = TaxiIdentifierService.getTaxiIdentifierService();
            Taxi taxi = taxiIdentifierService.getTaxi(carNumber);
            Driver driver = taxiIdentifierService.getDriver(taxi);
            long timeNow = System.currentTimeMillis();
            long rideTime = (long) (TimeCalculationService.getTimeCalculationService().getTime(distance) * 60000);
            rideStatisticsService.putRideToDatabase(client, driver, taxi, cost, distance,
                    new Date(timeNow), new Date(timeNow + rideTime));
            request.setAttribute("approved", true);
            request.setAttribute("cost", cost);
            request.setAttribute("distance", distance);
            request.setAttribute("discount", request.getParameter("discount"));
            request.setAttribute("taxi", taxi);
            request.setAttribute("driver", driver);
            request.setAttribute("arrivalTime", request.getParameter("arrivalTime"));
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

