package messages;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Handshake implements Externalizable
{
    public int peerId;
    public String protocolSig;

    public Handshake(){}

    public Handshake(int peerId)
    {
        this.peerId = peerId;
        protocolSig = "P2PFILESHARINGPROJ";
    }

    public static String createHandshake(int peerId)
    {
        String message = String.format("%s%d", "P2PFILESHARINGPROJ", peerId);
        return message;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeInt(peerId);
        out.writeObject(protocolSig);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        peerId = in.readInt();
        protocolSig = (String)in.readObject();
    }
}
