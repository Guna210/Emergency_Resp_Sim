package edu.curtin.DisasterSimulator;

import java.util.*;

public class Chemical implements Emergency
{
    private static final int CHEM_CLEANUP_TIME = 10;
    private static final int CHEM_CASUAL_PROB = 5;
    private static final int CHEM_CONTAM_PROB = 3;
    private EmergencyState state;
    private String location = " ";
    private int startTime = 0;
    private ResponderComm rComm;
    private boolean response = false;
    private int cleanCount = 0;
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
        rComm.send("chemical start "+location);
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
        if(cleanCount == CHEM_CLEANUP_TIME)
        {
            state = new End();
        }
        else
        {
            if(CHEM_CASUAL_PROB == numberGenerator(10))
            {
                casualtyCount = casualtyCount + 1;
                rComm.send("chemical casualty "+casualtyCount+" "+location);
            }
            if(CHEM_CONTAM_PROB == numberGenerator(5))
            {
                damageCount = damageCount + 1;
                rComm.send("chemical contam "+damageCount+" "+location);
            }
            if(response)
            {
                cleanCount = cleanCount + 1;
            }
        }
    }

    public int numberGenerator(int num)
    {
        SplittableRandom rand = new SplittableRandom();
        return rand.nextInt(num);
    }
}
