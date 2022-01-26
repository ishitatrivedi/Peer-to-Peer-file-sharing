package parser;
import java.util.HashMap;

public abstract class ConfigParser
{
    public abstract <T> HashMap<T, T> configParser();
}
