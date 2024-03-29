package edu.curtin.emergencysimulator.model;

import java.util.logging.*;

public class Chemical implements Emergency
{
    private static final int CHEM_CLEANUP_TIME = 10;
    private static final int CHEM_CASUAL_PROB = 20;
    private static final int CHEM_CONTAM_PROB = 25;
    private final Logger logger = Logger.getLogger(Chemical.class.getName());
    private EmergencyState state;
    private String location = " ";
    private int startTime = 0;
    private ResponderComm rComm;
    private boolean response = false;
    private int cleanCount = 0;
    private boolean endState = false;

    public Chemical()
    {
        // Initialize the state of chemical emergency to start
        logger.info("Initialize the state of chemical to Start");
        setState(new Start());
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
        // Let the responders know that a chemical emergency has started at a specific location
        rComm = resCom;
        logger.info(() -> "Inform responders that a chemical started at "+location);
        rComm.send("chemical start "+location);
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
        changeInState();
        if(response)
        {
            // Increment the cleanCount if responders are present
            logger.info(() -> "Increment the cleanCount of chemical at "+location+" by one");
            cleanCount = cleanCount + 1;
        }
        if(!response)
        {
            // If responders are absent, the cleanCount is changed to zero so that new responders
            // will start from the begining.
            cleanCount = 0;
        }
    }

    public void setEndState(boolean newEndState)
    {
        endState = newEndState;
    }
    
    public void changeInState()
    {
        state.changeInState(this, rComm);
    }

    public int getChemCleanTime()
    {
        return CHEM_CLEANUP_TIME;
    }

    public int getChemCasualty()
    {
        return CHEM_CASUAL_PROB;
    }

    public int getChemDamage()
    {
        return CHEM_CONTAM_PROB;
    }

    public int getCleanCount()
    {
        return cleanCount;
    }

    public boolean getResponse()
    {
        return response;
    }
}
