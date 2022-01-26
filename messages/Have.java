package messages;

import CNT5106Project.Helper;

public class Have extends Messages
{
    public int pieceNum;

    public Have()
    {
        super();
    }
    public Have(int pieceN)
    {
        super(Short.BYTES + Integer.BYTES, Helper.MessageValue.HAVE);
        pieceNum = pieceN;
    }
}
