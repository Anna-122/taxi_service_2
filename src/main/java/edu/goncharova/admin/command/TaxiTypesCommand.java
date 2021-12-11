package edu.goncharova.admin.command;

import edu.goncharova.admin.service.TaxiTypesService;
import edu.goncharova.command.Command;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TaxiTypesCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(TaxiTypesCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            TaxiTypesService taxiTypesService = TaxiTypesService.getTaxiTypesService();
            List<TaxiType> allTaxiTypes = taxiTypesService.getAllTaxiTypes();
            req.setAttribute("taxitypes",allTaxiTypes);
            req.getRequestDispatcher("taxitypes.jsp").forward(req,resp);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    @Override
    public String toString(){
        return this.getClass().getName();
    }
}
