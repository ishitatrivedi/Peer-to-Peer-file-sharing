import actors.Communicate;
import actors.Neighbors;
import messages.Messages;
import parser.CommonParser;
import parser.PeerInfoParser;
import parser.PeerInfoUtil;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import CNT5106Project.StoreData;
import messages.*;

public class peerProcess
{
    private HashMap<Integer, LinkedBlockingQueue<Messages>> uploaderQ;
    private HashMap<Integer, LinkedBlockingQueue<Messages>> downloaderQ;
    private LinkedBlockingQueue<PeerHave> broadcasterQ;

    public peerProcess()
    {
        uploaderQ = new HashMap<>();
        downloaderQ = new HashMap<>();
        broadcasterQ = new LinkedBlockingQueue<>();
    }

    public static void main(String[] args)
    {
        StoreData.peerId = Integer.parseInt(args[0]);
        File peerDir = new File("peer_" + StoreData.peerId);
        //boolean check = peerDir.mkdir();

        CommonParser commonParser = new CommonParser();
        HashMap<String, String> commonCFG = commonParser.configParser();

        PeerInfoParser peerInfoParser = new PeerInfoParser();
        TreeMap<Integer, PeerInfoUtil> peerCFG = peerInfoParser.configParser();

        // Initialize common configurations
        StoreData.numPreferredNeighbors = commonParser.getNumberOfPreferredNeighbors();
        StoreData.unchokingInterval = commonParser.getUnchokingInterval();
        StoreData.optimisticUCInterval = commonParser.getOptimisticUnchokingInterval();
        StoreData.fileName = commonParser.getFileName();
        StoreData.fileSize = commonParser.getFileSize();
        StoreData.pieceSize = commonParser.getPieceSize();

        // Initialize peer configurations
        StoreData.hostName = peerCFG.get(StoreData.peerId).getHostName();
        StoreData.listeningPort = peerCFG.get(StoreData.peerId).getListeningPort();
        StoreData.numOfPieces = (int) Math.ceil((double) StoreData.fileSize / StoreData.pieceSize);

        if (peerCFG.get(StoreData.peerId).getHasFile())
        {
            StoreData.setHasFullFile(true); // Set Peer has full file
        }
        else
        {
            StoreData.setHasFullFile(false); // Set false otherwise
        }

        // Setup Peer Process
        peerProcess myPeer = new peerProcess();

        ArrayList<Integer> peerIDList = new ArrayList<>();
        Iterator<Map.Entry<Integer, PeerInfoUtil>> entryIterator = peerCFG.entrySet().iterator();
        Map.Entry<Integer, PeerInfoUtil> entry = null;
        ArrayList<Communicate> communicatorsList = new ArrayList<>();
        ArrayList<Socket> sockets = new ArrayList<Socket>();

        // Client Connection
        //--------------------------------------------------------------------------------------------------------
        while (entryIterator.hasNext())
        {
            entry = entryIterator.next();
            peerIDList.add(entry.getKey());

            if(entry.getKey() == StoreData.peerId)
            {
                break;
            }

            myPeer.uploaderQ.put(entry.getKey(), new LinkedBlockingQueue<>());
            myPeer.downloaderQ.put(entry.getKey(), new LinkedBlockingQueue<>());
            Socket client = null;

            try
            {
                client = new Socket(entry.getValue().getHostName(), entry.getValue().getListeningPort());
                Communicate communicator = new Communicate(entry.getKey(), client, myPeer.uploaderQ.get(entry.getKey()), myPeer.uploaderQ.get(entry.getKey()), myPeer.broadcasterQ);
                communicatorsList.add(communicator);
                sockets.add(client);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        // Server Connection
        //--------------------------------------------------------------------------------------------------------
        ServerSocket serverSocket = null;
        try
        {
            serverSocket = new ServerSocket(StoreData.listeningPort);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        while (entryIterator.hasNext())
        {
            entry = entryIterator.next();
            peerIDList.add(entry.getKey());

            myPeer.uploaderQ.put(entry.getKey(), new LinkedBlockingQueue<>());
            myPeer.downloaderQ.put(entry.getKey(), new LinkedBlockingQueue<>());
            Socket server = null;
            try
            {
                server = new Socket(entry.getValue().getHostName(), entry.getValue().getListeningPort());
                Communicate communicator = new Communicate(entry.getKey(), server, myPeer.uploaderQ.get(entry.getKey()), myPeer.uploaderQ.get(entry.getKey()), myPeer.broadcasterQ);
                communicatorsList.add(communicator);
                sockets.add(server);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        StoreData.peerList = peerIDList;
        StoreData.initializePeersPayload();
        StoreData.initializeDownloadTime();

        for (Communicate communicator: communicatorsList)
        {
            (new Thread(communicator)).start();
        }

        Neighbors preferredNs = new Neighbors(myPeer.uploaderQ);
        Thread pNThread = new Thread(preferredNs);
        pNThread.start();
        // Need to implement optimistic Neighbors

        boolean loop = true;
        while(loop)
        {
            try
            {
                PeerHave broadcast = myPeer.broadcasterQ.take();
                if (StoreData.peerId == broadcast.getPeerId())
                {

                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }


        }
    }

    public void testParser()
    {
        // Test: Read Common Config File And Store Info in HashMap
        CommonParser temp = new CommonParser();
        temp.configParser();
        int getNeighbors = temp.getNumberOfPreferredNeighbors();
        int getUCInterval = temp.getUnchokingInterval();
        int getOptUCInterval = temp.getOptimisticUnchokingInterval();
        String getFName = temp.getFileName();
        int getFileSize = temp.getFileSize();
        int getPieceSize = temp.getPieceSize();

        System.out.println("Test 1");
        System.out.println("Num Neighbors: " + getNeighbors);
        System.out.println("UCInterval: " + getUCInterval);
        System.out.println("Opt UCInterval: " + getOptUCInterval);
        System.out.println("File Name: " + getFName);
        System.out.println("File Size: " + getFileSize);
        System.out.println("Piece Size: " + getPieceSize);
        System.out.println();

        // Test: Read PeerInfo Config File and Store Info in TreeMap
        PeerInfoParser peer = new PeerInfoParser();
        TreeMap<Integer, PeerInfoUtil> test = peer.configParser();
        int peerID = 1001;
        PeerInfoUtil test2 = test.get(peerID);
        String host = test2.getHostName();
        int listeningPort = test2.getListeningPort();
        boolean hasFile = test2.getHasFile();

        System.out.println("Test 2");
        System.out.println("Given peer Id: " + peerID);
        System.out.println("Host Name: " + host);
        System.out.println("Listening Port: " + listeningPort);
        System.out.println("Has File: " + hasFile);
        // --------------------------------------------------------------
    }
}
