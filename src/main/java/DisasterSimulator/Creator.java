package edu.curtin.DisasterSimulator;

import java.util.*;

public class Creator
{
    private List<String> eventList = new ArrayList<>();
    private Map<String, Emergency> emergencyMap = new HashMap<>();
    private Map<String, Emergency> activeMap = new HashMap<>();
    private List<String> removeList = new ArrayList<>();
    private List<String> responseList = new ArrayList<>();
    private int count = 0;
    
    public void eventCreator(List<String> list, Map<String, Emergency> map)
    {
        eventList = list;
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
                    activeMap.put(key, emg);
                }
            }
            responseList = resCom.poll();
            if(!responseList.isEmpty())
            {
                if(responseList.get(0).equals("end"))
                {
                    end = true;
                    break;
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
        for(String s : responseList)
        {
            String[] splitLine = s.split(" ",3);
            if(splitLine.length >= 3)
            {
                String reply = splitLine[0]+splitLine[2];
                if(activeMap.get(reply) != null)
                {
                    Emergency emg = activeMap.get(reply);
                    emg.respond(splitLine[1]);
                }
            }
        }
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
