package edu.curtin.DisasterSimulator;

import java.util.*;

public class Fire implements Emergency
{
    private static final int FIRE_LOW_TO_HIGH_TIME = 12;
    private static final int FIRE_LOW_CLEANP_TIME = 6;
    private static final int FIRE_HIGH_TO_LOW_TIME = 8;
    private static final int FIRE_LOW_CASUALTY_PROB = 3;
    private static final int FIRE_LOW_DAMAGE_PROB = 2;
    private static final int FIRE_HIGH_CASUALTY_PROB = 1;
    private static final int FIRE_HIGH_DAMAGE_PROB = 1;
    private EmergencyState state;
    private String location = " ";
    private int startTime = 0;
    private ResponderComm rComm;
    private boolean response = false;
    private int count = 0;
    private int cleanCount = 0;
    private int highCleanCount = 0;
    private int casualtyCount = 0;
    private int damageCount = 0;

    @Override
    public void setState(EmergencyState newState)
    {
        state = newState;
    }

    @Override
    public EmergencyState getState()
    {
        return state;
    }

    @Override
    public void setLocation(String newLocation)
    {
        location = newLocation;
        
    }

    @Override
    public String getLocation()
    {
        return location;
    }

    @Override
    public void setStartTime(int time)
    {
        startTime = time;
        
    }

    @Override
    public int getStartTime()
    {
        return startTime;
    }

    @Override
    public void initiate(ResponderComm resCom)
    {
        rComm = resCom;
        rComm.send("fire start "+location);
    }

    @Override
    public void respond(String res)
    {
        if(res.equals("+"))
        {
            response = true;
        }
        if(res.equals("-"))
        {
            response = false;
        }
    }

    @Override
    public void update()
    {
        if(cleanCount == FIRE_LOW_CLEANP_TIME)
        {
            state = new End();
            changeInState();
            // rComm.send("fire end "+location);
        }
        else
        {
            if(count == FIRE_LOW_TO_HIGH_TIME)
            {
                state = new HighIntensity();
                changeInState();
                // rComm.send("fire high "+location);
            }
            if(highCleanCount == FIRE_HIGH_TO_LOW_TIME)
            {
                state = new LowIntensity();
                count = 0;
                cleanCount = 0;
                highCleanCount = 0;
                changeInState();
                // rComm.send("fire low "+location);
            }
            if(state instanceof LowIntensity)
            {
                if(FIRE_LOW_CASUALTY_PROB == numberGenerator(5))
                {
                    casualtyCount = casualtyCount + 1;
                    rComm.send("fire casualty "+casualtyCount+" "+location);
                }
                if(FIRE_LOW_DAMAGE_PROB == numberGenerator(3))
                {
                    damageCount = damageCount + 1;
                    rComm.send("fire damage "+damageCount+" "+location);
                }
            }
            else
            {
                if(state instanceof HighIntensity)
                {
                    if(FIRE_HIGH_CASUALTY_PROB == numberGenerator(2))
                    {
                        casualtyCount = casualtyCount + 1;
                        rComm.send("fire casualty "+casualtyCount+" "+location);
                    }
                    if(FIRE_HIGH_DAMAGE_PROB == numberGenerator(2))
                    {
                        damageCount = damageCount + 1;
                        rComm.send("fire damage "+damageCount+" "+location);
                    }
                }
            }
            if((response)&&(state instanceof LowIntensity))
            {
                cleanCount = cleanCount + 1;
            }
            if((response)&&(state instanceof HighIntensity))
            {
                highCleanCount = highCleanCount + 1;
            }
        }
        count = count + 1;
    }
    
    public void changeInState()
    {
        state.changeInState(this, rComm);
    }

    public int numberGenerator(int num)
    {
        SplittableRandom rand = new SplittableRandom();
        return rand.nextInt(num);
    }
}
