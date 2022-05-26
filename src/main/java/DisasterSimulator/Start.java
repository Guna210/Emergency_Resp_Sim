package edu.curtin.DisasterSimulator;

import java.util.*;

public class Start implements EmergencyState
{
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
                flood.setState(new End());
                flood.changeInState();
            }
            else
            {
                if(!flood.getResponse())
                {
                    if(numberGenerator(100) < flood.getFloodCasualty())
                    {
                        casualtyCount = casualtyCount + 1;
                        resCom.send("flood casualty "+casualtyCount+" "+flood.getLocation());
                    }
                }
                if(numberGenerator(100) < flood.getFloodDamage())
                {
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
                chem.setState(new End());
                chem.changeInState();
            }
            else
            {
                if(numberGenerator(100) < chem.getChemCasualty())
                {
                    casualtyCount = casualtyCount + 1;
                    resCom.send("chemical casualty "+casualtyCount+" "+chem.getLocation());
                }
                if(numberGenerator(100) < chem.getChemDamage())
                {
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
