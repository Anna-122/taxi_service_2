package edu.goncharova.admin.command;

import edu.goncharova.admin.service.AddClientTypeService;
import edu.goncharova.command.Command;
import edu.goncharova.command.CommandFactory;
import edu.goncharova.domain.ClientType;
import edu.goncharova.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddClientTypeCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(AddClientTypeCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddClientTypeService addClientTypeService = AddClientTypeService.getAddClientTypeService();
        String name = request.getParameter("name");
        double moneyspent;
        int discount;
        try {
            moneyspent = Double.parseDouble(request.getParameter("moneyspent"));
            discount = Integer.parseInt(request.getParameter("discount"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Moneyspent and discount should be a number");
            request.getRequestDispatcher(CommandFactory.ADD_CLIENT_TYPE + ".jsp").forward(request, response);
            return;
        }
        if (discount > 100) {
            request.setAttribute("errorMessage", "Discount should be smaller than 100");
            request.getRequestDispatcher(CommandFactory.ADD_CLIENT_TYPE + ".jsp").forward(request, response);
            return;
        }
        if (discount < 0 || moneyspent < 0) {
            request.setAttribute("errorMessage", "Discount and money spent should be positive");
            request.getRequestDispatcher(CommandFactory.ADD_CLIENT_TYPE + ".jsp").forward(request, response);
            return;
        }
        if (name == null) {
            request.setAttribute("errorMessage", "You should fill all the fields");
            request.getRequestDispatcher(CommandFactory.ADD_CLIENT_TYPE + ".jsp").forward(request, response);
            return;
        }
        try {
            LOGGER.info("Trying to add clienttype");
            ClientType clientType = new ClientType(discount, name, moneyspent);
            addClientTypeService.addClientType(clientType);
            LOGGER.info("Successfully added clienttype");
            request.setAttribute("errorMessage", "ClientType added");
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

