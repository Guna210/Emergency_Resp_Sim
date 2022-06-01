package edu.curtin.emergencysimulator;

import java.util.*;
import java.io.*;
import java.util.logging.*;
import java.util.regex.*;
import edu.curtin.emergencysimulator.controller.*;
import edu.curtin.emergencysimulator.model.*;
import edu.curtin.emergencysimulator.view.*;

@SuppressWarnings("PMD.FieldNamingConventions")
// Logger is the one that triggers FieldNamingConventions. Tried changing the name of the logger but the issue still persists.

public class EmergencySimulatorApp
{
    private static final Pattern INPUT_PATTERN =Pattern.compile("([0-9]+ (fire|flood|chemical) .+)"); 
    private static final Logger logger = Logger.getLogger(EmergencySimulatorApp.class.getName());
    private static boolean isName = false;

    // In BlackBoard David has said not to suppress warnings to the whole class and instead to suppress the warning
    // just above the method it is triggred in. So the suppressWarning command were split into two.
    @SuppressWarnings("PMD.UnusedAssignment")
    public static void main(String[] args)
    {
        String fileName = " ";
        try
        {
            fileName = args[0];
            isName = true;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            // Informs the user that a file name was not provided.
            logger.info("No file name was provided by the user as a command line argument");
            System.out.println("No file name provided!");
        }

        Controller controller = new Controller();
        ResponderComm rComm = new ResponderCommImpl();
        
        // Check if the user has provided a file name.
        if(isName)
        {
            // If a file was provided, execute the program.
            try
            {
                List<String> list = readFile(fileName);
                Map<String, Emergency> emergencies = emergencyCreator(list);
                controller.control(rComm, emergencies);
            } 
            catch(EmergencySimulatorException e) 
            {
                System.out.println(e.getMessage());
            }
        }
    }

    /********************************************************************************************
     * Obtains the file name and reads the contents of the file. If file name is not valid      *
     * throws an exception.                                                                     *
     ********************************************************************************************/
    public static List<String> readFile(String fileName) throws EmergencySimulatorException
    {
        File file = new File(fileName);
        List<String> list = new ArrayList<>();
        try(Scanner sc = new Scanner(file))
        {
            // Read the contents of the input file line by line.
            while(sc.hasNextLine())
            {
                String line = sc.nextLine();
                list.add(line);
            }
        }
        catch(IOException e)
        {
            logger.info("Unable to open input file");
            throw new EmergencySimulatorException("File not found", e);
        }

        logger.info("Input File Completly read");
        return list;
    }

    /****************************************************************************************************
     * Obtains the list of inputs returned by the readFile method and creates relevant emergencies.      *
     * Throws an exception if an invalid statement is present in the input list.                        *
     ****************************************************************************************************/
    public static Map<String, Emergency> emergencyCreator(List<String> list) throws EmergencySimulatorException
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
                    // Create a new fire emergency at the specified location
                    int startTime = Integer.parseInt(splitLine[0]);
                    String key = splitLine[1]+splitLine[2];
                    Emergency emg = new Fire();
                    emg.setStartTime(startTime);
                    emg.setLocation(splitLine[2]);
                    // Ignores duplicates and adds new emergencies to the Map
                    if(emergencies.get(key) == null)
                    {
                        logger.info("Added a fire emergency");
                        emergencies.put(key, emg);
                    }
                }
                if(splitLine[1].equals("flood"))
                {
                    // Create a new flood emergency at the specified location
                    int startTime = Integer.parseInt(splitLine[0]);
                    String key = splitLine[1]+splitLine[2];
                    Emergency emg = new Flood();
                    emg.setStartTime(startTime);
                    emg.setLocation(splitLine[2]);
                    // Ignores duplicates and adds new emergencies to the Map
                    if(emergencies.get(key) == null)
                    {
                        logger.info("Added a flood emergency");
                        emergencies.put(key, emg);
                    }
                }
                if(splitLine[1].equals("chemical"))
                {
                    // Create a new chemical emergency at the specified location.
                    int startTime = Integer.parseInt(splitLine[0]);
                    String key = splitLine[1]+splitLine[2];
                    Emergency emg = new Chemical();
                    emg.setStartTime(startTime);
                    emg.setLocation(splitLine[2]);
                    // Ignores duplicates and adds new emergencies to the Map
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
                throw new EmergencySimulatorException("Invalid Input File!");
            }
        }
        return emergencies;
    }
}
