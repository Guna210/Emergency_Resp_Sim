package edu.curtin.emergencysimulator.model;

import java.util.*;
import java.util.logging.*;

public class LowIntensity implements EmergencyState
{
    private final Logger logger = Logger.getLogger(LowIntensity.class.getName());
    private int cleanCount = 0;

    @Override
    public void changeInState(Emergency emergency, ResponderComm resCom)
    {
        Fire fire = (Fire)emergency;
        // Check if the cleanCount is equal to the clean time and if so change the state of the fire to end.
        if(cleanCount == fire.getFireLowClean())
        {
            logger.info(() -> "Fire at "+fire.getLocation()+" has been cleaned");
            fire.setState(new End());
            fire.changeInState();
            fire.setEndState(true);
        }
        else
        {
            // Check if the count of the fire is equal to the time needed for a fire to progress to high intensity
            // and if so change the state of the fire to high intensity
            if(fire.getCount() == fire.getFireLowHigh())
            {
                logger.info(() -> "Fire at "+fire.getLocation()+" has progressed to HighIntensity");
                fire.setState(new HighIntensity());
                resCom.send("fire high "+fire.getLocation());
            }
            else
            {
                // check if a casualty should occur according to the casualty probability and if so tell the fire to 
                // increment the casualty count by one.
                if(numberGenerator(100) < fire.getFireLowCasualty())
                {
                    logger.info(() -> "Casualty occured at "+fire.getLocation()+" due to fire");
                    fire.incrementCasualty();
                    resCom.send("fire casualty "+fire.getCasualtyCount()+" "+fire.getLocation());
                }
                // check if a damage should occur according to the damage probability and if so tell the fire to 
                // increment the damage count by one.
                if(numberGenerator(100) < fire.getFireLowDamage())
                {
                    logger.info(() -> "Damage occured at "+fire.getLocation()+" due to fire");
                    fire.incrementDamage();
                    resCom.send("fire damage "+fire.getDamageCount()+" "+fire.getLocation());
                }
                // If responders are present increment the cleanCount by one.
                if(fire.getResponse())
                {
                    cleanCount = cleanCount + 1;
                }
                // If responders are absent, make cleanCount zero so that new responders that arrive will have to
                // start from the begining.
                if(!fire.getResponse())
                {
                    cleanCount = 0;
                }
            }
        }
    }
    
    /************************************************************************************************************
     * This method takes in a range of numbers and randomly generates a number within that specified range.     *
     * And the number returned is used to determine whether an event should occur.                              *
     * e.g : (If the range provided is 0-99, then the probability of obtaining a number less that 20 is 20%)    *
     ************************************************************************************************************/
    public int numberGenerator(int num)
    {
        SplittableRandom rand = new SplittableRandom();
        return rand.nextInt(num);
    }
}
