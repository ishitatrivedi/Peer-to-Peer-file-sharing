package parser;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class CommonParser
{
    private HashMap<String, String>  commonInfo = new HashMap<String, String>();
    private static String filePath = "config/Common.cfg";

    public CommonParser(){}

    public HashMap<String, String> configParser()
    {
        File commonCFGfile = new File(filePath);
        Scanner read = null;
        try
        {
            read = new Scanner(commonCFGfile);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }

        String line;
        while (read.hasNext())
        {
            line = read.nextLine();
            String[] split = line.split(" ");
            String key = split[0];
            String value = split[1];
            this.commonInfo.put(key, value);
        }
        read.close();
        return this.commonInfo;
    }

    public int getNumberOfPreferredNeighbors()
    {
        String read = this.commonInfo.get("NumberOfPreferredNeighbors");
        int value = Integer.parseInt(read);
        return value;
    }

    public int getUnchokingInterval()
    {
        String read = this.commonInfo.get("UnchokingInterval");
        int value = Integer.parseInt(read);
        return value;
    }

    public int getOptimisticUnchokingInterval()
    {
        String read = this.commonInfo.get("OptimisticUnchokingInterval");
        int value = Integer.parseInt(read);
        return value;
    }

    public String getFileName()
    {
        return this.commonInfo.get("FileName");
    }

    public int getFileSize()
    {
        String read = this.commonInfo.get("FileSize");
        int value = Integer.parseInt(read);
        return value;
    }

    public int getPieceSize()
    {
        String read = this.commonInfo.get("PieceSize");
        int value = Integer.parseInt(read);
        return value;
    }
}
