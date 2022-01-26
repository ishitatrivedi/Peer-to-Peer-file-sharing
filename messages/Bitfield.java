package messages;

import CNT5106Project.Helper;

public class Bitfield extends Messages
{
    private String payload;

    public Bitfield() {}

    public Bitfield(String payload)
    {
        super(payload.length() + Short.BYTES, Helper.MessageValue.BITFIELD);
        this.payload = payload;
    }

    public String getPayload()
    {
        return payload;
    }
}
