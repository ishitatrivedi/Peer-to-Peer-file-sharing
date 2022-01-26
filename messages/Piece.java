package messages;

import CNT5106Project.Helper;

import java.io.IOException;

public class Piece extends Messages
{
    public byte[] message;
    public int index;

    public Piece()
    {
        super();
    }

    public Piece(PiecePayload piecePayload)
    {
        super(piecePayload.message.length + Short.BYTES + Integer.BYTES, Helper.MessageValue.PIECE);
        this.message = piecePayload.message;
        this.index = piecePayload.index;
    }
    public Piece(int index, byte[] message) throws IOException, ClassNotFoundException
    {
        super(message.length + Short.BYTES + Integer.BYTES, Helper.MessageValue.PIECE);
        this.index = index;
        this.message = message;
    }

}
