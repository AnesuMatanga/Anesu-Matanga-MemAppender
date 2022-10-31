/**
 * Production class for Test cases
 */


import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class BusinessWorker {
    private static Logger LOGGER = Logger.getLogger(BusinessWorker.class.getName());

    public void generateLogs(String msg, int size) {
        for(int i = 0; i < size; i++) {
            LOGGER.trace(msg);
            LOGGER.debug(msg);
            LOGGER.info(msg);
            LOGGER.warn(msg);
            LOGGER.error(msg);
            LOGGER.debug(msg);
            LOGGER.info(msg);
            LOGGER.warn(msg);
            LOGGER.error(msg);
            LOGGER.info(msg);
        }

    }

    public void logsForLogger(String msg, int size){
        for (int i = 0; i < size; i++){
            LOGGER.trace(msg);
            LOGGER.info(msg);
            LOGGER.error(msg);
            LOGGER.warn(msg);
            LOGGER.fatal(msg);
            LOGGER.debug(msg);
        }
    }

    public void stressTestLogs(String msg, int size){
        for (int i = 0; i < size; i++){
            LOGGER.trace(msg);
            LOGGER.info(msg);
            LOGGER.error(msg);
            LOGGER.warn(msg);
            LOGGER.fatal(msg);
            LOGGER.debug(msg);
        }
    }
}
