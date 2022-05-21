package edu.curtin.DisasterSimulator;

public class Emergency
{
    private EmergencyState state;
    private String location = " ";
    private int startTime = 0;
    // private int endTime = 0;

    public int getStartTime()
    {
        return startTime;
    }

    public void setStartTime(int startTime)
    {
        this.startTime = startTime;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public void setState(EmergencyState newState)
    {
        state = newState;
    }

    public EmergencyState getState()
    {
        return state;
    }

    public void respond(String dep)
    {
        String arrival = " ";
        String f = " ";
        if(dep.equals("+"))
        {
            arrival = "arrived";
            f = "for";
        }
        else
        {
            if(dep.equals("-"))
            {
                arrival = "departed";
                f = "from";
            }
        }
        if(state instanceof LowIntensity)
        {
            System.out.println("Responders have "+arrival+" "+f+" the fire at "+location);
        }
        if(state instanceof FloodStart)
        {
            System.out.println("Responders have "+arrival+" "+f+" the flood at "+location);
        }
        if(state instanceof ChemStart)
        {
            System.out.println("Responders have "+arrival+" "+f+" the chemical at "+location);
        }
    }
}
