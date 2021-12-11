package edu.goncharova.admin.command;

import edu.goncharova.command.Command;
import edu.goncharova.domain.User;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.admin.service.UsersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UsersCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(UsersCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersService usersService = UsersService.getUsersService();
        try {
            String pageNumberParam = req.getParameter("pageNumber");
            String pageSizeParam = req.getParameter("pageSize");
            if (pageNumberParam == null) {
                pageNumberParam = "1";
            }
            if (pageSizeParam == null) pageSizeParam = "5";
            int pageNum = Integer.parseInt(pageNumberParam);
            int pageSize = Integer.parseInt(pageSizeParam);

            List<User> allUsers = usersService.getUsers((pageNum - 1) * pageSize, pageSize);
            req.setAttribute("pageAmount", ((usersService.getTableSize() + pageSize - 1) / pageSize));
            req.setAttribute("users", allUsers);
            req.getRequestDispatcher("users.jsp").forward(req, resp);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
