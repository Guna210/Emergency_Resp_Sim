package edu.curtin.DisasterSimulator;

import java.util.*;

public class Fire implements Emergency
{
    private static final int FIRE_LOW_TO_HIGH_TIME = 12;
    private static final int FIRE_LOW_CLEANP_TIME = 6;
    private static final int FIRE_HIGH_TO_LOW_TIME = 8;
    private static final int FIRE_LOW_CASUALTY_PROB = 20;
    private static final int FIRE_LOW_DAMAGE_PROB = 20;
    private static final int FIRE_HIGH_CASUALTY_PROB = 30;
    private static final int FIRE_HIGH_DAMAGE_PROB = 40;
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
        changeInState();
        count = count + 1;
    }
    
    public void changeInState()
    {
        state.changeInState(this, rComm);
    }

    public int getFireLowHigh()
    {
        return FIRE_LOW_TO_HIGH_TIME;
    }

    public int getFireLowClean()
    {
        return FIRE_LOW_CLEANP_TIME;
    }

    public int getFireHighLow()
    {
        return FIRE_HIGH_TO_LOW_TIME;
    }

    public int getFireLowCasualty()
    {
        return FIRE_LOW_CASUALTY_PROB;
    }

    public int getFireLowDamage()
    {
        return FIRE_LOW_DAMAGE_PROB;
    }

    public int getFireHighCasualty()
    {
        return FIRE_HIGH_CASUALTY_PROB;
    }

    public int getFireHighDamage()
    {
        return FIRE_HIGH_DAMAGE_PROB;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int newCount)
    {
        count = newCount;
    }

    public boolean getResponse()
    {
        return response;
    }

    public void incrementCasualty()
    {
        casualtyCount = casualtyCount + 1;
    }

    public void incrementDamage()
    {
        damageCount = damageCount + 1;
    }

    public int getCasualtyCount()
    {
        return casualtyCount;
    }

    public int getDamageCount()
    {
        return damageCount;
    }
}
