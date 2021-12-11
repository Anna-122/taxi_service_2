package edu.goncharova.admin.command;

import edu.goncharova.admin.service.DeleteService;
import edu.goncharova.command.Command;
import edu.goncharova.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(DeleteCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DeleteService deleteService = DeleteService.getDeleteService();
        int idToDelete = Integer.parseInt(request.getParameter("userid"));
        LOGGER.info("Trying to delete next user: userid={}",idToDelete);
        try {
            deleteService.delete(idToDelete);
            LOGGER.info("Successfully deleted");
            request.getRequestDispatcher("controller?command=users&pageNumber=1").forward(request,response);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    @Override
    public String toString(){
        return this.getClass().getName();
    }
}

