package edu.curtin.DisasterSimulator;

import java.util.logging.*;

public class Flood implements Emergency
{
    private static final int FLOOD_END_TIME = 15;
    private static final int FLOOD_DAMAGE_PROB = 30;
    private static final int FLOOD_CASUALTY_PROB = 20;
    private final Logger logger = Logger.getLogger(Flood.class.getName());
    private EmergencyState state;
    private String location = " ";
    private int startTime = 0;
    private ResponderComm rComm;
    private boolean response = false;
    private int count = 0;

    public Flood()
    {
        logger.info("Initialize the state of flood to Start");
        setState(new Start());
    }

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
        logger.info(() -> "Inform responders that a flood started at "+location);
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
        changeInState();
        logger.info(() -> "Increment the count of flood at "+location+" by one");
        count = count + 1;
    }

    public void changeInState()
    {
        state.changeInState(this, rComm);
    }

    public int getFloodEndTime()
    {
        return FLOOD_END_TIME;
    }

    public int getFloodCasualty()
    {
        return FLOOD_CASUALTY_PROB;
    }

    public int getFloodDamage()
    {
        return FLOOD_DAMAGE_PROB;
    }

    public int getCount()
    {
        return count;
    }

    public boolean getResponse()
    {
        return response;
    }
}
