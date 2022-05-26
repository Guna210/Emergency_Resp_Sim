package edu.curtin.DisasterSimulator;

import java.util.*;

public class LowIntensity implements EmergencyState
{
    private int cleanCount = 0;

    @Override
    public void changeInState(Emergency emergency, ResponderComm resCom)
    {
        Fire fire = (Fire)emergency;
        if(cleanCount == fire.getFireLowClean())
        {
            fire.setState(new End());
            fire.changeInState();
        }
        else
        {
            if(fire.getCount() == fire.getFireLowHigh())
            {
                fire.setState(new HighIntensity());
                resCom.send("fire high "+fire.getLocation());
            }
            else
            {
                if(numberGenerator(100) < fire.getFireLowCasualty())
                {
                    fire.incrementCasualty();
                    resCom.send("fire casualty "+fire.getCasualtyCount()+" "+fire.getLocation());
                }
                if(numberGenerator(100) < fire.getFireLowDamage())
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
    }
    
    public int numberGenerator(int num)
    {
        SplittableRandom rand = new SplittableRandom();
        return rand.nextInt(num);
    }
}
