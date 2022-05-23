package edu.curtin.DisasterSimulator;

public class FloodStart implements EmergencyState
{
    private int floodEnd = 20;

    @Override
    public void incrementCount(Emergency emergency, int count)
    {
        if(count == floodEnd)
        {
            emergency.setState(new End());
        }
        
    }
}
