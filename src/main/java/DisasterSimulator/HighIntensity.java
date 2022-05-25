package edu.curtin.DisasterSimulator;

public class HighIntensity implements EmergencyState
{

    @Override
    public void changeInState(Emergency emergency, ResponderComm resCom)
    {
        resCom.send("fire high "+emergency.getLocation());
    }
    
}
