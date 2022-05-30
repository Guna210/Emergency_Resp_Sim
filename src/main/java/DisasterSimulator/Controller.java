package edu.curtin.DisasterSimulator;

import java.util.*;
import java.util.logging.*;

public class Controller
{
    private final Logger logger = Logger.getLogger(Controller.class.getName());
    private Map<String, Emergency> emergencyMap = new HashMap<>();
    private Map<String, Emergency> activeMap = new HashMap<>();
    private List<String> removeList = new ArrayList<>();
    private List<String> responseList = new ArrayList<>();
    private int count = 0;
    
    /********************************************************************************************************
     * Hold the control logic of the simulation. Maintains a while loop that increments by one every        *
     * second to keep tarck of time.                                                                        *
     ********************************************************************************************************/
    public void control(ResponderComm rComm, Map<String, Emergency> map) throws DisasterSimulatorException
    {
        emergencyMap = map;
        ResponderComm resCom = rComm;
        boolean end = false;
        while(!end)
        {
            // If the start time of an emergency is equal to the count of the while loop, add that emergency
            // to the active list and send a message to the responders.
            for(String key : emergencyMap.keySet())
            {
                int startTime = emergencyMap.get(key).getStartTime();
                if(startTime == count)
                {
                    Emergency emg = emergencyMap.get(key);
                    emg.initiate(resCom);
                    logger.info("Add the started emergencies to the observers list");
                    activeMap.put(key, emg);
                }
            }
            // Check if the reply from responders is to end the simulation.
            logger.info("Get the reply from the responders");
            responseList = resCom.poll();
            if(!responseList.isEmpty())
            {
                if(responseList.get(0).equals("end"))
                {
                    logger.info("End the simulation according to the reply from responders");
                    end = true;
                    break;
                }
            }
            // According to the reply of the responders, inform the emergencies whether responders have arrived or not.
            for(String s : responseList)
            {
                String[] splitLine = s.split(" ",3);
                if(splitLine.length >= 3)
                {
                    String reply = splitLine[0]+splitLine[2];
                    if(emergencyMap.get(reply) != null)
                    {
                        Emergency emg = emergencyMap.get(reply);
                        emg.respond(splitLine[1]);
                    }
                }
            }
            notifyObservers();
            // Remove the emergencies that ended from the active list
            for(String key : removeList)
            {
                activeMap.remove(key);
            }
            count = count + 1;
            // Sleep the thread for one second to simulate the passage of time.
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                throw new DisasterSimulatorException("Unable to sleep thread!", e);
            }
        }
    }

    /****************************************************************************************************************
     * Inform all the emergencies on the active list that a second had passed and that they should increment        *
     * their count by one and put the emergencies that ended on that second to the remove list     .                *
     ****************************************************************************************************************/
    public void notifyObservers()
    {
        logger.info("Notify all the observers in the activeList that a second had passed.");
        for(String key : activeMap.keySet())
        {
            String reply = " ";
            Emergency emg = activeMap.get(key);
            emg.update();
            if(emg.getState() instanceof End)
            {
                removeList.add(key);
            }
        }
    }
}
