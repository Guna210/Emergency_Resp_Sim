package edu.curtin.DisasterSimulator;

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
        if(cleanCount == fire.getFireLowClean())
        {
            logger.info(() -> "Fire at "+fire.getLocation()+" has been cleaned");
            fire.setState(new End());
            fire.changeInState();
        }
        else
        {
            if(fire.getCount() == fire.getFireLowHigh())
            {
                logger.info(() -> "Fire at "+fire.getLocation()+" has progressed to HighIntensity");
                fire.setState(new HighIntensity());
                resCom.send("fire high "+fire.getLocation());
            }
            else
            {
                if(numberGenerator(100) < fire.getFireLowCasualty())
                {
                    logger.info(() -> "Casualty occured at "+fire.getLocation()+" due to fire");
                    fire.incrementCasualty();
                    resCom.send("fire casualty "+fire.getCasualtyCount()+" "+fire.getLocation());
                }
                if(numberGenerator(100) < fire.getFireLowDamage())
                {
                    logger.info(() -> "Damage occured at "+fire.getLocation()+" due to fire");
                    fire.incrementDamage();
                    resCom.send("fire damage "+fire.getDamageCount()+" "+fire.getLocation());
                }
                if(fire.getResponse())
                {
                    cleanCount = cleanCount + 1;
                }
                if(!fire.getResponse())
                {
                    cleanCount = 0;
                }
            }
        }
    }
    
    public int numberGenerator(int num)
    {
        SplittableRandom rand = new SplittableRandom();
        return rand.nextInt(num);
    }
}
