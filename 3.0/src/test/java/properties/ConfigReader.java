package properties;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader 
{
    public static Properties property;
    
    static
    {
        try 
        {
            InputStream input=Thread.currentThread().getContextClassLoader().getResourceAsStream("credentials.properties");
            property=new Properties();
            property.load(input);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
    }
    
    public static String getProperty(String key)
    {
        return property.getProperty(key);
    }
  
}
