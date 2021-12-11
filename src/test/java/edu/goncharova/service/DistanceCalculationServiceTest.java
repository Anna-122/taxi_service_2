package edu.goncharova.service;

import edu.goncharova.services.DistanceCalculationService;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class DistanceCalculationServiceTest {

    @Test
    public void getDistance_NotNull() {
        assertTrue(DistanceCalculationService.getDistanceCalculationService().getDistance("Kyiv", "Vinnytsia") > 0);
    }
}
