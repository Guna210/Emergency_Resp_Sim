package edu.curtin.emergencysimulator;

import java.util.logging.*;

public class Fire implements Emergency
{
    private static final int FIRE_LOW_TO_HIGH_TIME = 12;
    private static final int FIRE_LOW_CLEANP_TIME = 6;
    private static final int FIRE_HIGH_TO_LOW_TIME = 8;
    private static final int FIRE_LOW_CASUALTY_PROB = 20;
    private static final int FIRE_LOW_DAMAGE_PROB = 20;
    private static final int FIRE_HIGH_CASUALTY_PROB = 30;
    private static final int FIRE_HIGH_DAMAGE_PROB = 40;
    private final Logger logger = Logger.getLogger(Fire.class.getName());
    private EmergencyState state;
    private String location = " ";
    private int startTime = 0;
    private ResponderComm rComm;
    private boolean response = false;
    private int count = 0;
    private int casualtyCount = 0;
    private int damageCount = 0;
    private boolean endState = false;

    public Fire()
    {
        // Initialize the state of flood to LowIntensity
        logger.info("Initialize the state of the fire to LowIntensity");
        setState(new LowIntensity());
    }

    public void setState(EmergencyState newState)
    {
        state = newState;
    }

    @Override
    public boolean getEndState()
    {
        return endState;
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
        // Let the responders know that a fire has started at a specific location
        rComm = resCom;
        logger.info(() -> "Inform responders that a fire started at "+location);
        rComm.send("fire start "+location);
    }

    @Override
    public void respond(String res)
    {
        // Check whether responders have arrived or left to a specific location.
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
        // Increment the count of fire by one.
        changeInState();
        logger.info(() -> "Increment the count of fire at "+location+" by one");
        count = count + 1;
    }
    
    public void setEndState(boolean newEndState)
    {
        endState = newEndState;
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
        // Keep track of the number of casualties caused by a fire in both low and high intensities.
        casualtyCount = casualtyCount + 1;
    }

    public void incrementDamage()
    {
        // Keep track of the number of damages caused by a fire in both low and high intensities.
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
