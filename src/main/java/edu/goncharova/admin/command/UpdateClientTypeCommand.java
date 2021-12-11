package edu.goncharova.admin.command;

import edu.goncharova.admin.service.UpdateClientTypeService;
import edu.goncharova.command.Command;
import edu.goncharova.command.CommandFactory;
import edu.goncharova.domain.ClientType;
import edu.goncharova.command.LoginCommand;
import edu.goncharova.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateClientTypeCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientTypeId = Integer.parseInt(request.getParameter("clienttypeid"));
        String name = request.getParameter("name");
        double moneySpent;
        int discount;
        try {
            moneySpent = Double.parseDouble(request.getParameter("moneyspent"));
            discount = Integer.parseInt(request.getParameter("discount"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Moneyspent and discount should be a number");
            request.getRequestDispatcher(CommandFactory.UPDATE_CLIENT_TYPE + ".jsp").forward(request, response);
            return;
        }
        if (name == null) {
            request.setAttribute("errorMessage", "You should fill all the fields");
            request.getRequestDispatcher(CommandFactory.UPDATE_CLIENT_TYPE + ".jsp").forward(request, response);
            return;
        }
        UpdateClientTypeService updateClientTypeService = UpdateClientTypeService.getUpdateClientTypeService();
        try {
            LOGGER.info("Trying to update clienttype {}", clientTypeId);
            ClientType clientType = new ClientType(discount, name, moneySpent);
            clientType.setClientTypeId(clientTypeId);
            updateClientTypeService.updateClientType(clientType);
            LOGGER.info("Successfully updated clienttype {}", clientTypeId);
            request.setAttribute("errorMessage", "ClientType edited");
            request.getRequestDispatcher("controller?command=clienttypes").forward(request, response);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}

