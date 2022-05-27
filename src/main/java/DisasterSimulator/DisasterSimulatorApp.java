package edu.curtin.DisasterSimulator;

import java.util.*;
import java.io.*;
import java.util.logging.*;

public class DisasterSimulatorApp
{
    private static final Logger logger = Logger.getLogger(DisasterSimulatorApp.class.getName());

    public static void main(String[] args)
    {
        String fileName;
        List<String> list = new ArrayList<>();
        Map<String, Emergency> emergencies = new HashMap<>();
        int count = 0;
        Creator creator = new Creator();
        
        System.out.print("Enter the file name: ");

        try(Scanner sc = new Scanner(System.in)) 
        {
            fileName = sc.nextLine();
            list = readFile(fileName);
            emergencies = emergencyCreator(list);
            creator.eventCreator(emergencies);
        } 
        catch(DisasterSimulatorException e) 
        {
            System.out.println(e.getMessage());
        }
    }

    public static List<String> readFile(String fileName) throws DisasterSimulatorException
    {
        File file = new File(fileName);
        List<String> list = new ArrayList<>();
        String line = " ";
        try(Scanner sc = new Scanner(file))
        {
            while(sc.hasNextLine())
            {
                line = sc.nextLine();
                list.add(line);
            }
        }
        catch(IOException e)
        {
            throw new DisasterSimulatorException("File not found", e);
        }

        logger.info("Input File Completly read");
        return list;
    }

    public static Map<String, Emergency> emergencyCreator(List<String> list) throws DisasterSimulatorException
    {
        Map<String, Emergency> emergencies = new HashMap<>();
        for (String s : list)
        {
            String[] splitLine = s.split(" ",3);
            if(splitLine[1].equals("fire"))
            {
                int startTime = 0;
                try
                {
                    startTime = Integer.parseInt(splitLine[0]);
                }
                catch(NumberFormatException e)
                {
                    throw new DisasterSimulatorException("Invalid Input Structure!", e);
                }
                String key = splitLine[1]+splitLine[2];
                Emergency emg = new Fire();
                emg.setStartTime(startTime);
                emg.setLocation(splitLine[2]);
                if(emergencies.get(key) == null)
                {
                    logger.info("Added a fire emergency");
                    emergencies.put(key, emg);
                }
            }
            if(splitLine[1].equals("flood"))
            {
                int startTime = 0;
                try
                {
                    startTime = Integer.parseInt(splitLine[0]);
                }
                catch(NumberFormatException e)
                {
                    throw new DisasterSimulatorException("Invalid Input Structure!", e);
                }
                String key = splitLine[1]+splitLine[2];
                Emergency emg = new Flood();
                emg.setStartTime(startTime);
                emg.setLocation(splitLine[2]);
                if(emergencies.get(key) == null)
                {
                    logger.info("Added a flood emergency");
                    emergencies.put(key, emg);
                }
            }
            if(splitLine[1].equals("chemical"))
            {
                int startTime = 0;
                try
                {
                    startTime = Integer.parseInt(splitLine[0]);
                }
                catch(NumberFormatException e)
                {
                    throw new DisasterSimulatorException("Invalid Input Structure!", e);
                }
                String key = splitLine[1]+splitLine[2];
                Emergency emg = new Chemical();
                emg.setStartTime(startTime);
                emg.setLocation(splitLine[2]);
                if(emergencies.get(key) == null)
                {
                    logger.info("Added a chemical emergency");
                    emergencies.put(key, emg);
                }
            }
        }
        return emergencies;
    }
}
