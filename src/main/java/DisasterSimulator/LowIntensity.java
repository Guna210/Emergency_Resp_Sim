package edu.curtin.DisasterSimulator;

import java.util.Random;

public class LowIntensity implements EmergencyState
{

    @Override
    public void changeInState(Emergency emergency, ResponderComm resCom)
    {
        resCom.send("fire low "+emergency.getLocation());
    }
    
}
