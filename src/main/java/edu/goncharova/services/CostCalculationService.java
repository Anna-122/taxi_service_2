package edu.goncharova.services;


import edu.goncharova.domain.Client;
import edu.goncharova.domain.Taxi;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.command.CostCalculationCommand;
import edu.goncharova.exceptions.CostCalculationException;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.dao.ClientDAO;
import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.dao.TaxiTypeDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service for calculating cost of ride
 *
 * @see CostCalculationCommand
 */
public class CostCalculationService {
    private final static Logger LOGGER = LogManager.getLogger(CostCalculationService.class);
    private final static CostCalculationService COST_CALCULATION_SERVICE = new CostCalculationService();
    private final static double MIN_RIDE_COST = 10;
    private final IDAOFactory daoFactory;

    private CostCalculationService() {
        daoFactory = DAOFactory.getInstance();
    }

    /**
     * @return Instance of this class
     */
    public static CostCalculationService getCostCalculationService() {
        return COST_CALCULATION_SERVICE;
    }

    /**
     * @param userId Id of user to look for
     * @return Entity of Client
     * @throws DAOException Re-throws DAOException from ClientDAO
     * @see ClientDAO#findClientByUserId(int)
     */
    public Client getClient(int userId) throws DAOException {
        ClientDAO clientDAO = daoFactory.getClientDAO();
        return clientDAO.findClientByUserId(userId);
    }

    /**
     * @param taxi     Taxi which is supposed to drive client
     * @param distance Distance between start and finish
     * @param discount Client's discount
     * @return Cost of ride
     * @throws DAOException Re-throws DAOException from getFare method
     * @see #getFare(int)
     */
    public double getDriveCost(Taxi taxi, double distance, int discount) throws DAOException, CostCalculationException {
        double fare = getFare(taxi.getTaxiTypeId());
        double driveCost = ((MIN_RIDE_COST + (fare * distance)) / 100) * (100 - discount);
        return new BigDecimal(driveCost).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * @param taxiTypeId Id of taxitype to get fare for
     * @return Cost of 1 kilometer for specific taxitype
     * @throws DAOException Re-throws DAOException from TaxiTypeDAO
     * @see TaxiTypeDAO#findById(int)
     */
    public double getFare(int taxiTypeId) throws DAOException, CostCalculationException {
        TaxiTypeDAO taxiTypeDAO = daoFactory.getTaxiTypeDAO();
        TaxiType taxiType = taxiTypeDAO.findById(taxiTypeId);
        if (taxiType == null) {
            throw new CostCalculationException("Can't get type of taxi");
        }
        return taxiType.getFare();
    }
}
