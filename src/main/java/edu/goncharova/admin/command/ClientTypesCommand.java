package edu.goncharova.admin.command;

import edu.goncharova.admin.service.ClientTypesService;
import edu.goncharova.command.Command;
import edu.goncharova.domain.ClientType;
import edu.goncharova.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClientTypesCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(ClientTypesCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LOGGER.info("Showing all client types to admin");
            ClientTypesService clientTypesService = ClientTypesService.getClientTypesService();
            List<ClientType> allClientTypes = clientTypesService.getAllClientTypes();
            req.setAttribute("clienttypes", allClientTypes);
            req.getRequestDispatcher("clienttypes.jsp").forward(req, resp);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
