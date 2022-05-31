package edu.curtin.emergencysimulator;

public interface EmergencyState
{
    public void changeInState(Emergency emergency, ResponderComm resCom);
}
