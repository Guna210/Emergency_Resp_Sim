package edu.curtin.emergencysimulator.model;

public interface EmergencyState
{
    public void changeInState(Emergency emergency, ResponderComm resCom);
}
