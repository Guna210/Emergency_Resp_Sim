package edu.curtin.emergencysimulator;

public interface Emergency
{
    public boolean getEndState();
    public void setLocation(String newLocation);
    public String getLocation();
    public void setStartTime(int time);
    public int getStartTime();
    public void initiate(ResponderComm resCom);
    public void respond(String res);
    public void update();
}
