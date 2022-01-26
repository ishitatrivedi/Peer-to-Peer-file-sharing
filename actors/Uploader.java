package actors;

import CNT5106Project.Helper;
import messages.Messages;
import messages.PeerHave;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import static CNT5106Project.Helper.MessageValue.*;

public class Uploader implements Runnable
{
    private LinkedBlockingQueue<Messages> upQ;
    private Communicate communicator;
    private boolean stepFinished;

    public Uploader(int pId, LinkedBlockingQueue<Messages> upQ, LinkedBlockingQueue<PeerHave> bQ, Communicate com)
    {
        stepFinished = false;
        this.upQ = upQ;
        communicator = com;
    }

    public int getPeerId()
    {
        return communicator.getPeerId();
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
                buildMessage(upQ.take());
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
            Helper.MessageValue val = CNT5106Project.Helper.getMessageValue(message.val);
            if (val == CHOKE)
            {
                response = Messages.createChokeMessage();
            }
            else if (val == UNCHOKE)
            {
                response = Messages.createUnchokeMessage();
            }
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
