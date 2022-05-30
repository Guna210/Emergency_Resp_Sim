package edu.curtin.DisasterSimulator;

public interface Emergency
{
    public void setState(EmergencyState newState);
    public EmergencyState getState();
    public void setLocation(String newLocation);
    public String getLocation();
    public void setStartTime(int time);
    public int getStartTime();
    public void initiate(ResponderComm resCom);
    public void respond(String res);
    public void update();
}
