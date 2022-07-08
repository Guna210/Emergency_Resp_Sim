package edu.curtin.emergencysimulator.model;

public class End implements EmergencyState
{
    @Override
    public void changeInState(Emergency emergency, ResponderComm resCom)
    {
        if(emergency instanceof Fire)
        {
            resCom.send("fire end "+emergency.getLocation());
        }
        if(emergency instanceof Flood)
        {
            resCom.send("flood end "+emergency.getLocation());
        }
        if(emergency instanceof Chemical)
        {
            resCom.send("chemical end "+emergency.getLocation());
        }
    }

}
