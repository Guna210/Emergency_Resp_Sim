package edu.curtin.emergencysimulator.model;

public class EmergencySimulatorException extends Exception
{
    public EmergencySimulatorException(String msg)
    {
        super(msg);
    }

    public EmergencySimulatorException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
