import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Logs{
    private DateFormat timeFormat=new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
    private Date time =new Date();
    private BufferedWriter writer;

    public Logs(BufferedWriter writer){
        timeFormat= new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
        this.writer=writer;
    }

    public void tcp_connection(int id1, int id2){
        time = new Date();
        StringBuffer in=new StringBuffer();
        in.append(timeFormat.format(time));
        in.append(':');
        in.append("Peer");
        in.append(id1);
        in.append(" makes a connection to Peer ");
        in.append(id2);
        in.append('.');
        try{
            writer.write(in.toString());
            writer.newLine();
            writer.flush();
        }
        catch(Exception e){

        }
    }

    public void connection_to(int id1, int id2){
        time = new Date();
        StringBuffer in=new StringBuffer();
        in.append(timeFormat.format(time));
        in.append(':');
        in.append("Peer");
        in.append(id1);
        in.append(" is connected from Peer ");
        in.append(id2);
        in.append('.');
        try{
            writer.write(in.toString());
            writer.newLine();
            writer.flush();
        }
        catch(Exception e){

        }
    }

    public void change_of_preferred_neighbors(int id1, int[] id){
        time = new Date();
        StringBuffer in=new StringBuffer();
        in.append(timeFormat.format(time));
        in.append(':');
        in.append("Peer");
        in.append(id1);
        in.append(" has the preferred neighbors ");
        for(int i:id)
        {
            in.append(i);
            in.append(',');
        }
        in.deleteCharAt(in.length()-1);
        in.append('.');
        try{
            writer.write(in.toString());
            writer.newLine();
            writer.flush();
        }
        catch(Exception e){

        }
    }

    public void change_optimistically_unchoked_neighbor(int id1, int id2){
        time = new Date();
        StringBuffer in=new StringBuffer();
        in.append(timeFormat.format(time));
        in.append(':');
        in.append("Peer");
        in.append(id1);
        in.append(" has the optimistically unchoked neighbor ");
        in.append(id2);
        in.append('.');
        try{
            writer.write(in.toString());
            writer.newLine();
            writer.flush();
        }
        catch(Exception e){

        }
    }

    public void unchoking(int id1, int id2){
        time = new Date();
        StringBuffer in=new StringBuffer();
        in.append(timeFormat.format(time));
        in.append(':');
        in.append("Peer");
        in.append(id1);
        in.append(" is unchoked by ");
        in.append(id2);
        in.append('.');
        try{
            writer.write(in.toString());
            writer.newLine();
            writer.flush();
        }
        catch(Exception e){

        }
    }

    public void choking(int id1, int id2){
        time = new Date();
        StringBuffer in=new StringBuffer();
        in.append(timeFormat.format(time));
        in.append(':');
        in.append("Peer");
        in.append(id1);
        in.append(" is choked by ");
        in.append(id2);
        in.append('.');
        try{
            writer.write(in.toString());
            writer.newLine();
            writer.flush();
        }
        catch(Exception e){

        }
    }

    public void receiving_have_message(int id1, int id2, int index){
        time = new Date();
        StringBuffer in=new StringBuffer();
        in.append(timeFormat.format(time));
        in.append(':');
        in.append("Peer");
        in.append(id1);
        in.append(" received the 'have' message from ");
        in.append(id2);
        in.append(" for the piece ");
        in.append(index);
        in.append('.');
        try{
            writer.write(in.toString());
            writer.newLine();
            writer.flush();
        }
        catch(Exception e){

        }
    }

    public void receive_interested_message(int id1, int id2){
        time = new Date();
        StringBuffer in=new StringBuffer();
        in.append(timeFormat.format(time));
        in.append(':');
        in.append("Peer");
        in.append(id1);
        in.append(" received the 'interested' message ");
        in.append(id2);
        in.append('.');
        try{
            writer.write(in.toString());
            writer.newLine();
            writer.flush();
        }
        catch(Exception e){

        }
    }

    public void receive_notinterested_message(int id1, int id2){
        time = new Date();
        StringBuffer in=new StringBuffer();
        in.append(timeFormat.format(time));
        in.append(':');
        in.append("Peer");
        in.append(id1);
        in.append(" received the ' not interested' message ");
        in.append(id2);
        in.append('.');
        try{
            writer.write(in.toString());
            writer.newLine();
            writer.flush();
        }
        catch(Exception e){

        }
    }

    public void downloading_piece(int id1, int id2, int index, int num){
        time = new Date();
        StringBuffer in=new StringBuffer();
        in.append(timeFormat.format(time));
        in.append(':');
        in.append("Peer");
        in.append(id1);
        in.append(" has downloaded the piece ");
        in.append(index);
        in.append(" from ");
        in.append(id2);
        in.append('.');
        in.append(" Now the number of pieces it has is ");
        in.append(num);
        in.append('.');
        try{
            writer.write(in.toString());
            writer.newLine();
            writer.flush();
        }
        catch(Exception e){

        }
    }

    public void completion(int id1){
        time = new Date();
        StringBuffer in=new StringBuffer();
        in.append(timeFormat.format(time));
        in.append(':');
        in.append("Peer");
        in.append(id1);
        in.append(" has downloaded the complete file. ");
        try{
            writer.write(in.toString());
            writer.newLine();
            writer.flush();
        }
        catch(Exception e){

        }
    }


}