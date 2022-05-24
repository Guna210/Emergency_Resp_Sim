package edu.curtin.DisasterSimulator;

import java.util.*;

public class Flood implements Emergency
{
    private static final int FLOOD_END_TIME = 15;
    private static final int FLOOD_DAMAGE_PROB = 1;
    private static final int FLOOD_CASUALTY_PROB = 3;
    private EmergencyState state;
    private String location = " ";
    private int startTime = 0;
    private ResponderComm rComm;
    private boolean response = false;
    private int count = 0;
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
        rComm.send("flood start "+location);
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
        if(FLOOD_END_TIME == count)
        {
            state = new End();
            rComm.send("flood end "+location);
        }
        else
        {
            if(!response)
            {
                if(FLOOD_CASUALTY_PROB == numberGenerator(5))
                {
                    casualtyCount = casualtyCount + 1;
                    rComm.send("flood casualty "+casualtyCount+" "+location);
                }
            }
            if(FLOOD_DAMAGE_PROB == numberGenerator(3))
            {
                damageCount = damageCount + 1;
                rComm.send("flood damage "+damageCount+" "+location);
            }
        }
        count = count + 1;
    }

    public int numberGenerator(int num)
    {
        SplittableRandom rand = new SplittableRandom();
        return rand.nextInt(num);
    }
}
