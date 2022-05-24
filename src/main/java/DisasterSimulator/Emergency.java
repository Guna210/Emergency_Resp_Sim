package edu.curtin.DisasterSimulator;

public interface Emergency
{
    // private EmergencyState state;
    // private EmergencyState previousState;
    // private String location = " ";
    // private int startTime = 0;
    // // private int endTime = 0;
    // private int count = 0;
    // private boolean response = false;

    // public int getStartTime()
    // {
    //     return startTime;
    // }

    // public void setStartTime(int startTime)
    // {
    //     this.startTime = startTime;
    // }

    // public String getLocation()
    // {
    //     return location;
    // }

    // public void setLocation(String location)
    // {
    //     this.location = location;
    // }

    // public void setState(EmergencyState newState)
    // {
    //     state = newState;
    // }

    // public EmergencyState getState()
    // {
    //     return state;
    // }

    // public void setPreviousState(EmergencyState newState)
    // {
    //     previousState = newState;
    // }

    // public void respond(String dep)
    // {
    //     String arrival = " ";
    //     String f = " ";
    //     if(dep.equals("+"))
    //     {
    //         arrival = "arrived";
    //         f = "for";
    //         response = true;
    //     }
    //     else
    //     {
    //         if(dep.equals("-"))
    //         {
    //             arrival = "departed";
    //             f = "from";
    //             response = false;
    //         }
    //     }
    //     if(state instanceof LowIntensity)
    //     {
    //         System.out.println("Responders have "+arrival+" "+f+" the fire at "+location);
    //     }
    //     if(state instanceof FloodStart)
    //     {
    //         System.out.println("Responders have "+arrival+" "+f+" the flood at "+location);
    //     }
    //     if(state instanceof ChemStart)
    //     {
    //         System.out.println("Responders have "+arrival+" "+f+" the chemical at "+location);
    //     }
    // }

    // public boolean getResponse()
    // {
    //     return response;
    // }

    // public String incrementCount()
    // {
    //     String reply = " ";
    //     state.incrementCount(this, count);
    //     if(state instanceof End)
    //     {
    //         if(previousState instanceof LowIntensity)
    //         {
    //             reply = "fire end "+location;
    //         }
    //         if(previousState instanceof FloodStart)
    //         {
    //             reply = "flood end "+location;
    //         }
    //         if(previousState instanceof ChemStart)
    //         {
    //             reply = "chemical end "+location;
    //         }
    //     }
    //     // if(state instanceof HighIntensity)
    //     // {
    //     //     reply = "fire high "+location;
    //     // }
    //     if(state instanceof LowIntensity)
    //     {
    //         if(previousState instanceof HighIntensity)
    //         {
    //             reply = "fire low "+location;
    //         }
    //     }
    //     count = count + 1;
    //     return reply;
    // }

    public void setState(EmergencyState newState);
    public EmergencyState getState();
    public void setLocation(String newLocation);
    public String getLocation();
    public void setStartTime(int time);
    public int getStartTime();
}
