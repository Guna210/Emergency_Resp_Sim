package edu.curtin.DisasterSimulator;

public class Start implements EmergencyState
{

    @Override
    public void changeInState(Emergency emergency, ResponderComm resCom)
    {
        if(emergency instanceof Flood)
        {
            resCom.send("flood start "+emergency.getLocation());
        }
        if(emergency instanceof Chemical)
        {
            resCom.send("chemical start "+emergency.getLocation());
        }
    }

}
