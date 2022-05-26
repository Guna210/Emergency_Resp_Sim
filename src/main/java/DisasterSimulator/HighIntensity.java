package edu.curtin.DisasterSimulator;

import java.util.*;

public class HighIntensity implements EmergencyState
{
    private int cleanCount = 0;
    
    @Override
    public void changeInState(Emergency emergency, ResponderComm resCom)
    {
        Fire fire = (Fire)emergency;
        if(cleanCount == fire.getFireHighLow())
        {
            fire.setState(new LowIntensity());
            fire.setCount(0);
        }
        else
        {
            if(numberGenerator(100) < fire.getFireHighCasualty())
            {
                fire.incrementCasualty();
                resCom.send("fire casualty "+fire.getCasualtyCount()+" "+fire.getLocation());
            }
            if(numberGenerator(100) < fire.getFireHighDamage())
            {
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
