package edu.curtin.DisasterSimulator;
import java.util.*;
public interface ResponderComm
{
    List<String> poll();
    void send(String s);
}