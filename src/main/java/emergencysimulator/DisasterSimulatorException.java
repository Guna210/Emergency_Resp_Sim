package edu.curtin.emergencysimulator;

public class DisasterSimulatorException extends Exception
{
    public DisasterSimulatorException(String msg)
    {
        super(msg);
    }

    public DisasterSimulatorException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
