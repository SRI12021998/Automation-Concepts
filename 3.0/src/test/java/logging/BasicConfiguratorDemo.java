package logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class BasicConfiguratorDemo 
{
    public static Logger logger=LogManager.getLogger(BasicConfiguratorDemo.class);
    public static void main(String[] args) 
    {
        Configurator.setRootLevel(Level.INFO);
        logger.debug("This is a first debug log message but this will not be printed");
        logger.info("This is a first info log message");
        logger.warn("This is a first warn log message");
        logger.error("This is a first error log message");
        logger.fatal("This is a first fatal log message");
    }
}
