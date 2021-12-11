package edu.goncharova.servlet;

import edu.goncharova.command.Command;
import edu.goncharova.command.CommandFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletDispatcher extends HttpServlet {

    private final static Logger LOGGER = LogManager.getLogger(ServletDispatcher.class);

    @Override
    public void init() throws ServletException {
        super.init();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        CommandFactory factory = CommandFactory.getInstance();

        Command command = factory.getCommand(request.getParameter("command"));
        LOGGER.info("Got command {}", command);
        try {
            command.execute(request, response);
        } catch (ServletException | IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOGGER.error(e.getMessage());

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req, resp);
    }
}
