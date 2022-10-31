import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class StressTestAppender {
    private MemoryAppender memoryAppender;
    private Logger logger = Logger.getLogger(BusinessWorker.class.getName());
    private static List<LoggingEvent> newList = new LinkedList<>();

    @Before
    public void setup() {
        Logger logger = Logger.getLogger(BusinessWorker.class.getName());
        memoryAppender = new MemoryAppender(new VelocityLayout());
        memoryAppender.setThreshold(Level.TRACE);
        logger.setLevel(Level.TRACE);
        logger.addAppender(memoryAppender);
    }


    /**
     * Now Testing to check if getEventString is updated after
     * discarding logs and returns list of type String
     */
    //Test getEventStrings with extreme log events and see if it updates correctly
    @Test
    public void testGetCurrentLogsEXTREME(){
        memoryAppender.setMaxSize(30000);
        BusinessWorker worker = new BusinessWorker();
        worker.stressTestLogs("MSG", 103);
        //System.out.println("Size: " + memoryAppender.getCurrentLogs().size());
        Assert.assertEquals(618, memoryAppender.getCurrentLogs().size());
        memoryAppender.reset();
    }

    //Test getEventStrings with extreme log events
    @Test
    public void testGetEventStringExtreme(){
        memoryAppender.setMaxSize(7);
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs("MSG", 79851);
        //System.out.println("Size: " + memoryAppender.getEventStrings().size());
        Assert.assertEquals(6, memoryAppender.getEventStrings().size());
        memoryAppender.reset();
    }

    //Test to see how the appender works with different logger levels and Extreme log events
    @Test
    public void testLoggerWARNEffectOnAppender(){
        logger.setLevel(Level.WARN);
        memoryAppender.setMaxSize(7);
        BusinessWorker worker = new BusinessWorker();
        worker.stressTestLogs("Big Problem!", 832652);
        //System.out.println("Size: " + memoryAppender.getCurrentLogs().size());
        //Since level is WARN- getCurrentLogs size should return 3
        Assert.assertEquals(6, memoryAppender.getCurrentLogs().size());
        memoryAppender.reset();
    }


}

