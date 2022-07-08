package edu.curtin.emergencysimulator.model;
import java.util.*;
public interface ResponderComm
{
    List<String> poll();
    void send(String s);
}