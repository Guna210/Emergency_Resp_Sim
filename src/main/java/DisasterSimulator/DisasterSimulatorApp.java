package edu.curtin.DisasterSimulator;

import java.util.*;
import java.io.*;
import java.util.logging.*;
import java.util.regex.*;

public class DisasterSimulatorApp
{
    private static final Pattern INPUT_PATTERN =Pattern.compile("([0-9]+ (fire|flood|chemical) .+)"); 
    private static final Logger logger = Logger.getLogger(DisasterSimulatorApp.class.getName());
    private static boolean name = false;

    public static void main(String[] args)
    {
        String fileName = " ";
        try
        {
            fileName = args[0];
            name = true;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            logger.info("No file name was provided by the user as a command line argument");
            System.out.println("No file name provided!");
        }

        List<String> list = new ArrayList<>();
        Map<String, Emergency> emergencies = new HashMap<>();
        int count = 0;
        Controller controller = new Controller();
        ResponderComm rComm = new ResponderCommImpl();
        
        if(name)
        {
            try
            {
                list = readFile(fileName);
                emergencies = emergencyCreator(list);
                controller.control(rComm, emergencies);
            } 
            catch(DisasterSimulatorException e) 
            {
                System.out.println(e.getMessage());
            }
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
            logger.info("Unable to open input file");
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
            Matcher matcher = INPUT_PATTERN.matcher(s);
            if(matcher.matches())
            {
                String[] splitLine = s.split(" ",3);
                if(splitLine[1].equals("fire"))
                {
                    int startTime = Integer.parseInt(splitLine[0]);
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
                    int startTime = Integer.parseInt(splitLine[0]);
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
                    int startTime = Integer.parseInt(splitLine[0]);
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
            else
            {
                logger.info("Invalid input file provided");
                throw new DisasterSimulatorException("Invalid Input File!");
            }
        }
        return emergencies;
    }
}
