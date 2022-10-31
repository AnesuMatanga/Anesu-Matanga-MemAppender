
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class TestAppender {
    private MemoryAppender memoryAppender;
    private Logger logger = Logger.getLogger(BusinessWorker.class.getName());
    private Class<TestAppender> BusinessWorker;

    @Before
    public void setup() {
        Logger logger = Logger.getLogger(BusinessWorker.class.getName());
        memoryAppender = new MemoryAppender(new PatternLayout());
        memoryAppender.setThreshold(Level.TRACE);
        logger.setLevel(Level.TRACE);
        logger.addAppender(memoryAppender);
        memoryAppender.reset();
    }

    //Test using Small Size
    /**   @Test
    public void testSmallMaxSize() {
    memoryAppender.reset();
    memoryAppender.resetDiscardedLogs();
    memoryAppender.setMaxSize(5);
    BusinessWorker worker = new BusinessWorker();
    worker.generateLogs("MSG", 1);
    Assert.assertEquals(5,memoryAppender.getDiscardedLogsCount());
    } **/

    //Test using large max size()
    @Test
    public void testBigMaxSize() {
        memoryAppender.setMaxSize(100);
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs("MSG", 12);
        Assert.assertTrue(memoryAppender.getDiscardedLogsCount() == 100);
        memoryAppender.reset();
    }

    //Test that fails and throws Assertion error
   /* @Test
    public void testFAILDiscardLogs() throws AssertionError{
        memoryAppender.setMaxSize(100);
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs("MSG", 12);
        Assert.assertTrue( memoryAppender.getDiscardedLogsCount() != 100);
        memoryAppender.reset();
    }*/

    /**
     * Now Testing to check if getCurrentLogs is updated after
     * discarding logs
     */
    //Test small size logging events
    @Test
    public void testGetCurrentLogsSmall(){
        memoryAppender.setMaxSize(5);
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs("MSG", 1);
        Assert.assertEquals(5, memoryAppender.getCurrentLogs().size());
        memoryAppender.reset();
    }

    //Test large size logging events method is getCurrentLogs
    @Test
    public void testGetCurrentLogsBig(){
        memoryAppender.setMaxSize(7);
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs("MSG", 138);
        Assert.assertEquals(1, memoryAppender.getCurrentLogs().size());
        memoryAppender.reset();
    }

    /**
     * Now Testing to check if getEventString is updated after
     * discarding logs and returns list of type String
     */
    //Test getEventStrings with few log events
    @Test
    public void testGetEventStringSmall(){
        memoryAppender.setMaxSize(5);
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs("MSG", 1);
        Assert.assertEquals(5, memoryAppender.getEventStrings().size());
        memoryAppender.reset();
    }

    //Test getEventStrings with more log events
    @Test
    public void testGetEventStringBig(){
        memoryAppender.setMaxSize(7);
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs("MSG", 138);
        Assert.assertEquals(1, memoryAppender.getEventStrings().size());
        memoryAppender.reset();
    }

    /**
     * Now Testing to check if appender and logger working together
     *
     */
    //Test to see how the appender works with different logger levels and log events
    @Test
    public void testLoggerWARNEffectOnAppender(){
        logger.setLevel(Level.WARN);
        memoryAppender.setMaxSize(7);
        BusinessWorker worker = new BusinessWorker();
        worker.logsForLogger("Big Problem!", 1);
        //Since level is WARN- getCurrentLogs size should return 3
        Assert.assertEquals(3, memoryAppender.getCurrentLogs().size());
        memoryAppender.reset();
    }

    //When Trace is the Logger level, logs should be == 6
    @Test
    public void testLoggerTRACEEffectOnAppender(){
        logger.setLevel(Level.TRACE);
        memoryAppender.setMaxSize(7);
        BusinessWorker worker = new BusinessWorker();
        worker.logsForLogger("Big Problem!", 1);
        //Since level is TRACE- getCurrentLogs size should return 6
        Assert.assertFalse(memoryAppender.getCurrentLogs().size() == 3);
        Assert.assertTrue(memoryAppender.getCurrentLogs().size() == 6);
        memoryAppender.reset();
    }

    //Check how appender doesn't put in list when threshold is different level
    @Test
    public void testAppenderThreshold(){
        logger.setLevel(Level.TRACE);
        memoryAppender.setThreshold(Level.WARN);
        memoryAppender.setMaxSize(7);
        BusinessWorker worker = new BusinessWorker();
        worker.logsForLogger("Big Problem!", 1);
        //Since level is WARN- getCurrentLogs size should return 3
        Assert.assertTrue(memoryAppender.getCurrentLogs().size() == 3);
        Assert.assertFalse(memoryAppender.getCurrentLogs().size() == 6);
        memoryAppender.reset();
    }

    //Testing Velocity layout working with MemoryAppender
    @Test
    public void testVelocityLayout(){
        logger.setLevel(Level.FATAL);
        memoryAppender.setThreshold(Level.FATAL);
        memoryAppender.setLayout(new VelocityLayout("$p: $m"));
        memoryAppender.setMaxSize(7);
        BusinessWorker worker = new BusinessWorker();
        worker.logsForLogger("Big Problem!", 1);
        //getVelocityLayoutFormat should return string in the same format as the given velocity layout in memAppender
        Assert.assertEquals("FATAL: Big Problem!", memoryAppender.getVelocityLayoutFormat(Level.FATAL));
        Assert.assertFalse(memoryAppender.getVelocityLayoutFormat(Level.FATAL) == "DEBUG: Big Problem!");
        memoryAppender.reset();
    }

}























