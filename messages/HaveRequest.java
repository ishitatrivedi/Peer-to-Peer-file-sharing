package messages;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class HaveRequest implements Externalizable
{
    int pos;

    public HaveRequest(){}

    public HaveRequest(int index)
    {
        pos = index;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(pos);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        pos = in.readInt();
    }
}
