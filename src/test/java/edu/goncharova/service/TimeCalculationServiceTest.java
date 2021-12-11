package edu.goncharova.service;

import edu.goncharova.services.TimeCalculationService;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TimeCalculationServiceTest {

    @Test
    public void getTimeWithPoints_ReturnsNonZero(){
        assertTrue(TimeCalculationService.getTimeCalculationService().getTime("Kyiv","Vinnytsia")>0);
    }
    @Test
    public void getTimeWithDistance_ReturnsNonZero(){
        assertTrue(TimeCalculationService.getTimeCalculationService().getTime(13)>0);
    }
}
