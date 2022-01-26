package messages;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class PiecePayload implements Externalizable
{
    public byte[] message;
    public int index;

    public PiecePayload(){}

    public PiecePayload(int index, byte[] message)
    {
        this.message = message;
        this.index = index;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(message);
        out.writeObject(index);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        message = (byte[])in.readObject();
        index = (int)in.readObject();
    }
}
