package edu.curtin.DisasterSimulator;

import java.util.*;
import java.util.logging.*;

public class HighIntensity implements EmergencyState
{
    private final Logger logger = Logger.getLogger(HighIntensity.class.getName());
    private int cleanCount = 0;
    
    @Override
    public void changeInState(Emergency emergency, ResponderComm resCom)
    {
        Fire fire = (Fire)emergency;
        if(cleanCount == fire.getFireHighLow())
        {
            logger.info(() -> "Fire at "+fire.getLocation()+" has been reduced to LowIntensity");
            fire.setState(new LowIntensity());
            fire.setCount(0);
        }
        else
        {
            if(numberGenerator(100) < fire.getFireHighCasualty())
            {
                logger.info(() -> "Casualty occured at "+fire.getLocation()+" due to fire");
                fire.incrementCasualty();
                resCom.send("fire casualty "+fire.getCasualtyCount()+" "+fire.getLocation());
            }
            if(numberGenerator(100) < fire.getFireHighDamage())
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
    
    public int numberGenerator(int num)
    {
        SplittableRandom rand = new SplittableRandom();
        return rand.nextInt(num);
    }
}
