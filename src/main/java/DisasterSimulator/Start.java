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
        if(emergency instanceof Flood)
        {
            Flood flood = (Flood)emergency;
            if(flood.getFloodEndTime() == flood.getCount())
            {
                logger.info(() -> "Flood at "+flood.getLocation()+" has ended");
                flood.setState(new End());
                flood.changeInState();
            }
            else
            {
                if(!flood.getResponse())
                {
                    if(numberGenerator(100) < flood.getFloodCasualty())
                    {
                        logger.info(() -> "Casualty occured at "+flood.getLocation()+" due to flood");
                        casualtyCount = casualtyCount + 1;
                        resCom.send("flood casualty "+casualtyCount+" "+flood.getLocation());
                    }
                }
                if(numberGenerator(100) < flood.getFloodDamage())
                {
                    logger.info(() -> "Damage occured at "+flood.getLocation()+" due to flood");
                    damageCount = damageCount + 1;
                    resCom.send("flood damage "+damageCount+" "+flood.getLocation());
                }
            }
        }
        if(emergency instanceof Chemical)
        {
            Chemical chem = (Chemical)emergency;
            if(chem.getCleanCount() == chem.getChemCleanTime())
            {
                logger.info(() -> "Chemical at "+chem.getLocation()+" has been cleaned");
                chem.setState(new End());
                chem.changeInState();
            }
            else
            {
                if(numberGenerator(100) < chem.getChemCasualty())
                {
                    logger.info(() -> "Casualty occured at "+chem.getLocation()+" due to chemical");
                    casualtyCount = casualtyCount + 1;
                    resCom.send("chemical casualty "+casualtyCount+" "+chem.getLocation());
                }
                if(numberGenerator(100) < chem.getChemDamage())
                {
                    logger.info(() -> "Damage occured at "+chem.getLocation()+" due to chemical");
                    damageCount = damageCount + 1;
                    resCom.send("chemical contam "+damageCount+" "+chem.getLocation());
                }
            }
        }
    }

    public int numberGenerator(int num)
    {
        SplittableRandom rand = new SplittableRandom();
        return rand.nextInt(num);
    }
}
