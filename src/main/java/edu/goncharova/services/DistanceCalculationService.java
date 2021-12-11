package edu.goncharova.services;

import edu.goncharova.command.CostCalculationCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Random;

/**
 * Service for calculating distance between two objects
 * Not implemented now, returns random distance
 * @see CostCalculationCommand
 */
public class DistanceCalculationService {
    private final static Logger LOGGER = LogManager.getLogger(DistanceCalculationService.class);
    private final static DistanceCalculationService DISTANCE_CALCULATION_SERVICE = new DistanceCalculationService();
    private DistanceCalculationService(){
    }
    /**
     *
     * @return Instance of this class
     */
    public static DistanceCalculationService getDistanceCalculationService(){
        return DISTANCE_CALCULATION_SERVICE;
    }

    /**
     *
     * @param from Starting point
     * @param to Finishing point
     * @return Distance in kilometers
     */
    public double getDistance(String from, String to) {
        return new BigDecimal(new Random(new Date().getTime()).nextFloat()*new Random(23).nextInt(100)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
