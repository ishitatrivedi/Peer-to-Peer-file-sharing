package parser;

import java.io.*;
import java.util.Scanner;
import java.util.TreeMap;

public class PeerInfoParser
{
    private static String filePath = "config/PeerInfo.cfg";

    public PeerInfoParser(){}

    public TreeMap<Integer, PeerInfoUtil> configParser()
    {
        TreeMap<Integer, PeerInfoUtil> peerInfoTree = new TreeMap<Integer, PeerInfoUtil>();
        File peerInfoCFGfile = new File(filePath);

        Scanner read = null;
        try
        {
            read = new Scanner(peerInfoCFGfile);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }

        String line;
        while (read.hasNext())
        {
            line = read.nextLine();
            String[] split = line.split(" ");

            // Peer ID = Key of TreeMap
            int key = Integer.parseInt(split[0]);

            // Host name
            String hostName = split[1];

            // Listening Port
            int listeningPort = Integer.parseInt(split[2]);

            // Has File Bit
            int hasFileBit = Integer.parseInt(split[3]);
            boolean hasFile = hasFileBit == 0? false: true;

            PeerInfoUtil peerInfoUtil = new PeerInfoUtil(hostName, listeningPort, hasFile);
            peerInfoTree.put(key, peerInfoUtil);
        }
        read.close();

        return peerInfoTree;
    }
}
