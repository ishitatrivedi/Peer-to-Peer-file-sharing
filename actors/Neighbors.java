package actors;

import CNT5106Project.StoreData;
import messages.Messages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

// K preferred neighbors
public class Neighbors implements Runnable
{
    private boolean stepFinished;
    private HashMap<Integer, LinkedBlockingQueue<Messages>> uploaderQ;

    public Neighbors(HashMap<Integer, LinkedBlockingQueue<Messages>> uploader)
    {
        uploaderQ = uploader;
        stepFinished = false;
    }
    public boolean getStepFinished()
    {
        return stepFinished;
    }
    public void setStepFinished(boolean finished)
    {
        stepFinished = finished;
    }

    @Override
    public void run()
    {
        preferredNeighbors();
    }

    public void preferredNeighbors()
    {
        HashSet<Integer> prefNeighbors = new HashSet<>();

        while(!getStepFinished())
        {
            HashSet<Integer> oldN = null; // Old Neighbors
            HashSet<Integer> currentN = null; // Current Neighbors
            HashSet<Integer> newN = null; // New Neighbors

            try
            {
                if(!StoreData.getHasFullFile())
                {
                    Thread.sleep(StoreData.unchokingInterval);
                    currentN = StoreData.getPreferredNeighbors();
                }
                else
                {
                    currentN = StoreData.getRandomNeighbors();
                }

                if (prefNeighbors.size() == 0)
                {
                    prefNeighbors = currentN;
                    newN = currentN;
                    oldN = new HashSet<>();
                }
                else
                {
                    oldN = new HashSet<>(prefNeighbors);
                    oldN.removeAll(currentN);
                    newN = new HashSet<>(currentN);
                    newN.removeAll(prefNeighbors);
                    prefNeighbors = currentN;
                }

                for(int pId: oldN)
                {
                    uploaderQ.get(pId).put(Messages.createChokeMessage());
                }
                for(int pId: newN)
                {
                    uploaderQ.get(pId).put(Messages.createUnchokeMessage());
                }

                if(StoreData.getHasFullFile())
                {
                    Thread.sleep(StoreData.unchokingInterval);
                }
            }
            catch (InterruptedException x)
            {
                x.printStackTrace();
            }

        }
    }
}
