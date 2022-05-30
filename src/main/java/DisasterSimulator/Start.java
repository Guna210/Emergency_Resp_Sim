package edu.curtin.DisasterSimulator;

import java.util.*;
import java.util.logging.*;

public class Start implements EmergencyState
{
    private final Logger logger = Logger.getLogger(Start.class.getName());
    private int casualtyCount = 0;
    private int damageCount = 0;

    @Override
    public void changeInState(Emergency emergency, ResponderComm resCom)
    {
        // Checks if the emergency is a flood.
        if(emergency instanceof Flood)
        {
            Flood flood = (Flood)emergency;
            // Check if the count of flood is equal to the flood end time and if so change the state 
            // of the flood to end.
            if(flood.getFloodEndTime() == flood.getCount())
            {
                logger.info(() -> "Flood at "+flood.getLocation()+" has ended");
                flood.setState(new End());
                flood.changeInState();
            }
            else
            {
                // Check if responders are there or not for a casualty to occur.
                if(!flood.getResponse())
                {
                    // Checks if a casualty should occur according to the casualty probability and if so
                    // increment the casualty count by one.
                    if(numberGenerator(100) < flood.getFloodCasualty())
                    {
                        logger.info(() -> "Casualty occured at "+flood.getLocation()+" due to flood");
                        casualtyCount = casualtyCount + 1;
                        resCom.send("flood casualty "+casualtyCount+" "+flood.getLocation());
                    }
                }
                // Checks if a damage should occur according to the damage probability and if so
                // increment the damage count by one.
                if(numberGenerator(100) < flood.getFloodDamage())
                {
                    logger.info(() -> "Damage occured at "+flood.getLocation()+" due to flood");
                    damageCount = damageCount + 1;
                    resCom.send("flood damage "+damageCount+" "+flood.getLocation());
                }
            }
        }
        // Check if the emergency is a chemical emergency.
        if(emergency instanceof Chemical)
        {
            Chemical chem = (Chemical)emergency;
            // Check if cleanCount is equal to the clean time of the emergency and if so change the state
            // of the chemical emergency to end.
            if(chem.getCleanCount() == chem.getChemCleanTime())
            {
                logger.info(() -> "Chemical at "+chem.getLocation()+" has been cleaned");
                chem.setState(new End());
                chem.changeInState();
            }
            else
            {
                // Checks if a casualty should occur according to the casualty probability and if so
                // increment the casualty count by one.
                if(numberGenerator(100) < chem.getChemCasualty())
                {
                    logger.info(() -> "Casualty occured at "+chem.getLocation()+" due to chemical");
                    casualtyCount = casualtyCount + 1;
                    resCom.send("chemical casualty "+casualtyCount+" "+chem.getLocation());
                }
                // Checks if a damage should occur according to the damage probability and if so
                // increment the damage count by one.
                if(numberGenerator(100) < chem.getChemDamage())
                {
                    logger.info(() -> "Damage occured at "+chem.getLocation()+" due to chemical");
                    damageCount = damageCount + 1;
                    resCom.send("chemical contam "+damageCount+" "+chem.getLocation());
                }
            }
        }
    }

    /************************************************************************************************************
     * This method takes in a range of numbers and randomly generates a number within that specified range.     *
     * And the number returned is used to determine whether an should occur.                                    *
     * e.g : (If the range provided is 0-99, then the probability of obtaining a number less that 20 is 20%)    *
     ************************************************************************************************************/
    public int numberGenerator(int num)
    {
        SplittableRandom rand = new SplittableRandom();
        return rand.nextInt(num);
    }
}
