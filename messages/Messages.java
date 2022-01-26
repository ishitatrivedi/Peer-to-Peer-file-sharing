package messages;

import CNT5106Project.Helper;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Messages implements Externalizable
{
    public int size;
    public short val;

    public Messages() {}

    public Messages(int s, Helper.MessageValue temp)
    {
        this.size = s;
        this.val = temp.value;
    }

    public static Messages createChokeMessage()
    {
        return new Messages(1, Helper.MessageValue.CHOKE);
    }

    public static Messages createUnchokeMessage()
    {
        return new Messages(1, Helper.MessageValue.UNCHOKE);
    }

    public static Messages createInterestedMessage()
    {
        return new Messages(1, Helper.MessageValue.INTERESTED);
    }

    public static Messages createNotInterestedMessage()
    {
        return new Messages(1, Helper.MessageValue.NOT_INTERESTED);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException
    {
        size = in.readInt();
        val = in.readShort();
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeInt(size);
        out.writeShort(val);
    }
}
