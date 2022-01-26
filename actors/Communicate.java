package actors;

import CNT5106Project.StoreData;
import messages.Bitfield;
import messages.Handshake;
import messages.Messages;
import messages.PeerHave;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Communicate implements Runnable
{
    private int peerId;
    private String hostName;
    private int listeningPort;
    private Socket socket;
    private LinkedBlockingQueue<Messages> uploaderQ;
    private LinkedBlockingQueue<Messages> downloaderQ;
    private LinkedBlockingQueue<PeerHave> broadCasterQ;

    private ObjectInputStream inObject;
    private ObjectOutputStream outObject;
    private InputStream inStream;
    private OutputStream outStream;
    private boolean stepFinished;

    public Communicate (int pId, Socket s, LinkedBlockingQueue<Messages> uploader, LinkedBlockingQueue<Messages> downloader, LinkedBlockingQueue<PeerHave> broadcaster) throws IOException
    {
        // Initialize
        peerId = pId;
        socket = s;
        uploaderQ = uploader;
        downloaderQ = downloader;
        broadcaster = broadCasterQ;

        inStream = s.getInputStream();
        outStream = s.getOutputStream();
        inObject = new ObjectInputStream(inStream);
        outObject = new ObjectOutputStream(outStream);
        stepFinished = false;
    }

    public Communicate (int pId, String host, int lPort, Socket s, LinkedBlockingQueue<Messages> uploader, LinkedBlockingQueue<Messages> downloader, LinkedBlockingQueue<PeerHave> broadcaster) throws IOException
    {
        // Initialize
        peerId = pId;
        hostName = host;
        listeningPort = lPort;
        socket = s;
        uploaderQ = uploader;
        downloaderQ = downloader;
        broadcaster = broadCasterQ;

        inStream = s.getInputStream();
        outStream = s.getOutputStream();
        inObject = new ObjectInputStream(inStream);
        outObject = new ObjectOutputStream(outStream);
        stepFinished = false;
    }

    @Override
    public void run()
    {
        try
        {
            sendHandshake(); // Send Handshake
            Bitfield peerBFMessage = exchangeBitfield();

            // If peer is interesting send interested message
            if(StoreData.isPeerInteresting(peerBFMessage))
            {
                sendInterested();
            }
            else
            {
                sendNotInterested();
            }

            //Uploader uploader = new Uploader(peerId, uploaderQ, broadCasterQ, this);
        }
        catch (ClassNotFoundException x)
        {
            x.printStackTrace();
        }
        catch (IOException x)
        {
            x.printStackTrace();
        }
        catch (InterruptedException x)
        {
            x.printStackTrace();
        }
    }

    public int getPeerId()
    {
        return peerId;
    }
    public boolean getStepFinished()
    {
        return stepFinished;
    }
    public void setStepFinished(boolean input)
    {
        stepFinished = input;
    }

    // Write in Socket
    public synchronized void writeInSocket(Externalizable message) throws IOException
    {
        outObject.writeObject(message);
    }
    public void closeSocket() throws IOException
    {
        if(socket != null)
        {
            socket.close();
        }
    }

    private boolean sendHandshake() throws IOException, ClassNotFoundException
    {
        Handshake handshake = new Handshake(StoreData.peerId);
        writeInSocket((handshake));
        Handshake peerHandshake = (Handshake) inObject.readObject();
        return true;
    }

    private Bitfield exchangeBitfield() throws IOException, InterruptedException, ClassNotFoundException
    {
        Bitfield bF = new Bitfield(StoreData.getPieceInfo());
        writeInSocket(bF);
        Thread.sleep(100);
        Bitfield bF2 = (Bitfield) inObject.readObject();
        return bF2;
    }

    private void sendInterested() throws IOException
    {
        writeInSocket(Messages.createInterestedMessage());
    }
    private void sendNotInterested() throws IOException
    {
        writeInSocket(Messages.createNotInterestedMessage());
    }

}
