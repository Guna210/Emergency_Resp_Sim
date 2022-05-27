package edu.curtin.DisasterSimulator;

import java.util.*;
import java.util.logging.*;

public class Creator
{
    private final Logger logger = Logger.getLogger(Creator.class.getName());
    private Map<String, Emergency> emergencyMap = new HashMap<>();
    private Map<String, Emergency> activeMap = new HashMap<>();
    private List<String> removeList = new ArrayList<>();
    private List<String> responseList = new ArrayList<>();
    private int count = 0;
    
    public void eventCreator(Map<String, Emergency> map)
    {
        emergencyMap = map;
        ResponderComm resCom = new ResponderCommImpl();
        boolean end = false;
        while(!end)
        {
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
            for(String key : removeList)
            {
                activeMap.remove(key);
            }
            count = count + 1;
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

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
