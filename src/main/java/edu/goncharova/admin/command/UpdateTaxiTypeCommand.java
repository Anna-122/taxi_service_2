package edu.goncharova.admin.command;

import edu.goncharova.admin.service.UpdateTaxiTypeService;
import edu.goncharova.command.Command;
import edu.goncharova.command.CommandFactory;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.command.LoginCommand;
import edu.goncharova.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateTaxiTypeCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taxiTypeId = Integer.parseInt(request.getParameter("taxitypeid"));
        String taxiTypeName = request.getParameter("name");
        double fare;
        try {
            fare = Double.parseDouble(request.getParameter("fare"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Fare should be a number");
            request.getRequestDispatcher(CommandFactory.UPDATE_TAXI_TYPE + ".jsp").forward(request, response);
            return;
        }
        if (taxiTypeName == null) {
            request.setAttribute("errorMessage", "You should fill all the fields");
            request.getRequestDispatcher(CommandFactory.UPDATE_TAXI_TYPE + ".jsp").forward(request, response);
            return;
        }
        UpdateTaxiTypeService updateTaxiTypeService = UpdateTaxiTypeService.getTaxiTypeService();
        try {
            LOGGER.info("Trying to update taxitype {}", taxiTypeId);
            TaxiType taxiType = new TaxiType(fare, taxiTypeName);
            taxiType.setTaxiTypeId(taxiTypeId);
            updateTaxiTypeService.updateTaxiType(taxiType);
            LOGGER.info("Successfully updated taxitype {}", taxiTypeId);
            request.setAttribute("errorMessage", "TaxiType edited");
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

