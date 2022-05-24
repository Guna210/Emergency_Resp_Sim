package edu.curtin.DisasterSimulator;

import java.util.*;
import java.io.*;

public class DisasterSimulatorApp
{
    private static int max = 0;
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
        } 
        catch(DisasterSimulatorException e) 
        {
            System.out.println(e.getMessage());
        }
        creator.eventCreator(list, emergencies);
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
                String[] splitLine = line.split(" ", 3);
                int temp = Integer.parseInt(splitLine[0]);
                if(temp > max)
                {
                    max = temp;
                }
                list.add(line);
            }
        }
        catch(IOException e)
        {
            throw new DisasterSimulatorException("File not found", e);
        }
       
        return list;
    }

    public static Map<String, Emergency> emergencyCreator(List<String> list)
    {
        Map<String, Emergency> emergencies = new HashMap<>();
        for (String s : list)
        {
            String[] splitLine = s.split(" ",3);
            if(splitLine[1].equals("fire"))
            {
                int startTime = Integer.parseInt(splitLine[0]);
                String key = splitLine[1]+splitLine[2];
                Emergency emg = new Fire();
                EmergencyState emgState = new LowIntensity();
                emg.setState(emgState);
                // emg.setPreviousState(emgState);
                emg.setStartTime(startTime);
                emg.setLocation(splitLine[2]);
                emergencies.put(key, emg);
            }
            if(splitLine[1].equals("flood"))
            {
                int startTime = Integer.parseInt(splitLine[0]);
                String key = splitLine[1]+splitLine[2];
                Emergency emg = new Flood();
                EmergencyState emgState = new FloodStart();
                emg.setState(emgState);
                // emg.setPreviousState(emgState);
                emg.setStartTime(startTime);
                emg.setLocation(splitLine[2]);
                emergencies.put(key, emg);
            }
            if(splitLine[1].equals("chemical"))
            {
                int startTime = Integer.parseInt(splitLine[0]);
                String key = splitLine[1]+splitLine[2];
                Emergency emg = new Chemical();
                EmergencyState emgState = new ChemStart();
                emg.setState(emgState);
                // emg.setPreviousState(emgState);
                emg.setStartTime(startTime);
                emg.setLocation(splitLine[2]);
                emergencies.put(key, emg);
            }
        }
        return emergencies;
    }
}
