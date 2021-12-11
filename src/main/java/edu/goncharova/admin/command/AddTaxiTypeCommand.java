package edu.goncharova.admin.command;

import edu.goncharova.admin.service.AddTaxiTypeService;
import edu.goncharova.command.Command;
import edu.goncharova.command.CommandFactory;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddTaxiTypeCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(AddTaxiTypeCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddTaxiTypeService addTaxiTypeService = AddTaxiTypeService.getAddTaxiTypeService();
        String taxiTypeName = request.getParameter("name");
        double fare;
        try {
            fare = Double.parseDouble(request.getParameter("fare"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Fare should be a number");
            request.getRequestDispatcher(CommandFactory.ADD_TAXI_TYPE + ".jsp").forward(request, response);
            return;
        }
        if (fare < 0.0) {
            request.setAttribute("errorMessage", "Fare should be a positive number");
            request.getRequestDispatcher(CommandFactory.ADD_TAXI_TYPE + ".jsp").forward(request, response);
            return;
        }
        if (taxiTypeName == null) {
            request.setAttribute("errorMessage", "You should fill all the fields");
            request.getRequestDispatcher(CommandFactory.ADD_TAXI_TYPE + ".jsp").forward(request, response);
            return;
        }
        try {
            LOGGER.info("Trying to add taxitype");
            TaxiType taxi = new TaxiType(fare, taxiTypeName);
            addTaxiTypeService.addTaxiType(taxi);
            LOGGER.info("Successfully added taxitype");
            request.setAttribute("errorMessage", "TaxiType added");
            request.getRequestDispatcher("controller?command=taxitypes").forward(request, response);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}

