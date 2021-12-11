package edu.goncharova.services;

import edu.goncharova.dao.ClientTypeDAO;
import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.domain.Client;
import edu.goncharova.command.CostCalculationCommand;
import edu.goncharova.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service for determining the type of discount to apply for specific client
 * @see CostCalculationCommand
 */
public class ClientTypeCalculationService {
    private final static Logger LOGGER = LogManager.getLogger(ClientTypeCalculationService.class);
    private final static ClientTypeCalculationService COST_CALCULATION_SERVICE = new ClientTypeCalculationService();
    private final IDAOFactory daoFactory;
    private ClientTypeCalculationService(){
        daoFactory= DAOFactory.getInstance();
    }

    /**
     *
     * @return Instance of this class
     */
    public static ClientTypeCalculationService getCostCalculationService(){
        return COST_CALCULATION_SERVICE;
    }

    /**
     *
     * @param client Client for which we are calculating discount
     * @return Discount for client based on money spent before
     * @throws DAOException Re-throws DAOException from ClientTypeDAO
     * @see ClientTypeDAO#findDiscountByMoneySpent(double)
     */
    public int getClientsDiscount(Client client) throws DAOException {
        double moneySpent = RideStatisticsService.getRideStatisticsService().getClientsSpentMoney(client);
        ClientTypeDAO clientTypeDAO = daoFactory.getClientTypeDAO();
        return clientTypeDAO.findDiscountByMoneySpent(moneySpent);
    }
}
