package messages;

import CNT5106Project.Helper;

public class Request extends Messages
{
    public int index;

    public Request()
    {
        super();
    }
    public Request(HaveRequest payload)
    {
        super(Short.BYTES + Integer.BYTES, Helper.MessageValue.REQUEST);
        this.index = payload.pos;
    }
}
