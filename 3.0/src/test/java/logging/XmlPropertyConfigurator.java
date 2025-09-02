package logging;

import org.apache.logging.log4j.*;

import org.apache.logging.log4j.LogManager;

public class XmlPropertyConfigurator 
{
    public static Logger logger=LogManager.getLogger(XmlPropertyConfigurator.class);
    public static void main(String[] args) 
    {
        logger.debug("checking debug");
        logger.info("checking info");
        logger.warn("checking info");
        logger.error("checking info");
        logger.fatal("checking info");
    }
}
