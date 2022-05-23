package edu.curtin.DisasterSimulator;

public class ChemStart implements EmergencyState
{
    private int CHEM_CLEANUP_TIME = 10;
    private int cleanTime = 0;
    
    @Override
    public void incrementCount(Emergency emergency, int count)
    {
        if(emergency.getResponse())
        {
            if(cleanTime == CHEM_CLEANUP_TIME)
            {
                emergency.setState(new End());
            }
            else
            {
                cleanTime = cleanTime + 1;
            }
        }
        
    }
    
}
