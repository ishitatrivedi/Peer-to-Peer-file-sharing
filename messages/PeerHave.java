package messages;

public class PeerHave extends Have
{
    private int peerId;

    public PeerHave(int pId, Have message)
    {
        super(message.pieceNum);
        peerId = pId;
    }

    public int getPeerId()
    {
        return peerId;
    }
}
