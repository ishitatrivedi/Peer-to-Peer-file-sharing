package parser;

public class PeerInfoUtil
{
    private String host;
    private int listeningPort;
    private boolean hasFile;

    // Holds Peer Information
    // ID is stored as TreeMap key
    public PeerInfoUtil(String host, int listeningPort, boolean hasFile)
    {
        this.host = host;
        this.listeningPort = listeningPort;
        this.hasFile = hasFile;
    }

    public String getHostName()
    {
        return host;
    }
    public int getListeningPort()
    {
        return listeningPort;
    }
    public boolean getHasFile()
    {
        return hasFile;
    }
}
