package messages;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class BitfieldPayload implements Externalizable
{
    String payload;

    public BitfieldPayload(){}

    public BitfieldPayload(String arg)
    {
        payload = arg;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(payload);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        payload = (String) in.readObject();
    }
}
