package edu.curtin.DisasterSimulator;

import java.util.Random;

public class LowIntensity implements EmergencyState
{
    private int lowToHigh = 12;
    private int lowCleaNP = 8;
    private int cleanTime = 0;
    private int lowCasualty = 0;

    @Override
    public void incrementCount(Emergency emergency, int count)
    {
        if(count == lowToHigh)
        {
            emergency.setState(new HighIntensity());
            emergency.setPreviousState(this);
        }
        if(emergency.getResponse())
        {
            if(cleanTime == lowCleaNP)
            {
                emergency.setState(new End());
            }
            else
            {
                cleanTime = cleanTime + 1;
            }
        }
        else
        {
            cleanTime = 0;
        }
        if(numberGenerator() == 3)
        {
            lowCasualty = lowCasualty + 1;
        }
    }

    public int numberGenerator()
    {
        Random rand = new Random();
        int retValue = rand.nextInt(5);
        return retValue;
    }
    
}
