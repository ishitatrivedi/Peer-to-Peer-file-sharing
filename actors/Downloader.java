package actors;

import messages.Messages;
import messages.PeerHave;

import java.util.concurrent.LinkedBlockingQueue;

import static CNT5106Project.Helper.MessageValue.*;

public class Downloader implements Runnable
{
    private int peerId;
    private LinkedBlockingQueue<Messages> dlQ;
    private LinkedBlockingQueue<PeerHave> broadcaster;
    private Communicate communicator;
    private boolean unchoke;
    private boolean stepFinished;

    public Downloader(int pId, LinkedBlockingQueue<Messages> dlQ, LinkedBlockingQueue<PeerHave> bQ, Communicate com)
    {
        stepFinished = false;
        unchoke = false;
        peerId = pId;
        this.dlQ = dlQ;
        broadcaster = bQ;
        communicator = com;
    }

    public boolean getUnchoked()
    {
        return unchoke;
    }
    public void setUnchoked(boolean arg)
    {
        unchoke = arg;
    }
    public boolean getStepFinished()
    {
        return stepFinished;
    }
    public void setStepFinished(boolean arg)
    {
        stepFinished = arg;
    }

    @Override
    public void run()
    {
        while (!getStepFinished())
        {
            try
            {
                buildMessage(dlQ.take());
            }
            catch (ClassNotFoundException x)
            {
                x.printStackTrace();
            }
            catch (InterruptedException x)
            {
                x.printStackTrace();
            }
        }
    }

    public void buildMessage(Messages message) throws InterruptedException, ClassNotFoundException
    {
        Messages response = null;
        //try
        //{
        CNT5106Project.Helper.MessageValue val = CNT5106Project.Helper.getMessageValue(message.val);
        if (val == CHOKE)
        {
            setUnchoked(false);
        }
        else if (val == UNCHOKE) {}
        else if (val == HAVE) {}
        else if (val == PIECE) {}
        else if (val == SHUT_DOWN)
        {
            setStepFinished(true);
        }
        else {}
        //}
        //catch (IOException x)
        //{
        //x.printStackTrace();
        //}
    }

}
