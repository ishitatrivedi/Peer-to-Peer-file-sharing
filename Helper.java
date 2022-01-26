package CNT5106Project;

public class Helper
{
    public static enum MessageValue
    {
        CHOKE((short) 0, "CHOKE"),
        UNCHOKE((short) 1, "UNCHOKE"),
        INTERESTED((short) 2, "INTERESTED"),
        NOT_INTERESTED((short) 3, "NOT_INTERESTED"),
        HAVE((short) 4, "HAVE"),
        BITFIELD((short) 5, "BITFIELD"),
        REQUEST((short) 6, "REQUEST"),
        PIECE((short) 7, "PIECE"),
        NOT_FOUND((short) 8, "NOT_FOUND"),
        SHUT_DOWN((short) -1, "SHUT_DOWN")
        ;

        MessageValue(short value, String description)
        {
            this.value = value;
            this.description = description;
        }

        public final short value;
        public final String description;

        short getMessageValue()
        {
            return value;
        }
    }

    public static MessageValue getMessageValue(short val)
    {
        if (val == 0) return MessageValue.CHOKE;
        else if (val == 1) return MessageValue.UNCHOKE;
        else if (val == 2) return MessageValue.INTERESTED;
        else if (val == 3) return MessageValue.NOT_INTERESTED;
        else if (val == 4) return MessageValue.HAVE;
        else if (val == 5) return MessageValue.BITFIELD;
        else if (val == 6) return MessageValue.REQUEST;
        else if (val == 7) return MessageValue.PIECE;
        else return MessageValue.NOT_FOUND;
    }
}
