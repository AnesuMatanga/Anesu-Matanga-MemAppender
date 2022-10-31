import org.apache.log4j.*;
import org.apache.log4j.spi.LoggingEvent;

import java.io.Console;
import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.logging.ConsoleHandler;


public class MemoryAppender extends AppenderSkeleton {
    private static MemoryAppender memoryAppender = null;

    private long discardedLogs = 0;
    private long discardAfterReset = 0;
    private int maxSize = 5;
    private List<LoggingEvent> logEventsList = null;
    private final List<String> logEventString = new ArrayList<>();
    private LoggingEvent logEvent;
    private Console console;
    private Layout SUPPLIED_LAYOUT;
    private boolean isNewLayout = false;


    //Constructors
    public MemoryAppender() {
        logEventsList = new ArrayList<>();
    }

    public MemoryAppender(Layout layout) {
        setLayout(layout);
        logEventsList = new ArrayList<>();
    }

    public MemoryAppender(Layout layout, List<LoggingEvent> logList){
        logEventsList = logList;
    }


    /**
     * @param loggingEvent adding to list of logs(memory)
     */
    @Override
    protected void append(LoggingEvent loggingEvent) {
        VelocityLayout layout;
        logEvent = loggingEvent;
        if (this.closed == false) {
            if (logEventsList.size() >= maxSize) {
                //System.out.println("Exceeded max size!!");
                //System.out.println("Size: " + getCurrentLogsSize());
                reset();
                logEvent.getMessage();
                logEvent.getLoggerName();
                logEvent.getLevel();
                logEventsList.add(logEvent);
                logEventString.add(String.valueOf(logEvent));
                //System.out.println(loggingEvent.getMessage());
            } else {
                logEvent.getMessage();
                logEvent.getLoggerName();
                logEvent.getLevel();
                logEventsList.add(logEvent);
                logEventString.add(String.valueOf(logEvent));
                //System.out.println(loggingEvent.getMessage());

            }
        } else {
            System.out.println("Memory Appender is closed");
        }
    }

    public void setMaxSize(int size){
        this.maxSize = size;
    }

    public void setLayout(Layout layout){
        SUPPLIED_LAYOUT = layout;
        isNewLayout = true;
    }

    /**
     * Close the memory appender after use
     */
    @Override
    public void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
    }

    /**
     * @return boolean of requiresLayout?
     */
    @Override
    public boolean requiresLayout() {
        return false;
    }

    public String getPATTERNLayout() {
        return PatternLayout.DEFAULT_CONVERSION_PATTERN;
    }


    public List<String> getEventStrings() {
        return Collections.unmodifiableList(logEventString);
    }

    //Return VelocityLayoutFormatted message
    public String getVelocityLayoutFormat(Level level){
        VelocityLayout velocityLayout = new VelocityLayout();
        String getBack;
        int position = 0;
        for (int i = 0; i < getCurrentLogs().size(); i++) {
            if(getCurrentLogs().get(i).getLevel().toString() == "level"){
                position = i;
            }
        }
        getBack = velocityLayout.format(getCurrentLogs().get(position));
        return getBack;
    }


    @Override
    public String toString() {
        return logEvent.getLevel() + logEvent.getLoggerName() + logEvent.getMessage();

    }

    public void printLogs() {
        PatternLayout patternLayout = new PatternLayout();
        //System.out.println("In printLogs");
        int size = getCurrentLogs().size();
        String printLogs = "";
        if (isNewLayout == true) {
            for (int i = 0; i < size; i++) {
                printLogs = SUPPLIED_LAYOUT.format(getCurrentLogs().get(i));
                System.out.print(printLogs);
            }
        } else {
            for (int i = 0; i < size; i++) {
                printLogs = patternLayout.format(getCurrentLogs().get(i));
                System.out.print(printLogs);
            }

        }
    }

    //resetCachedDiscardLogs is to reset and discardedLogs number
    public void resetDiscardedLogs(){
        discardedLogs = 0;
    }


    /**
     * @return List<LoggingEvent> list of logs unmodifiable
     */
    public List<LoggingEvent> getCurrentLogs() {
        return Collections.unmodifiableList(logEventsList);
    }

    /**
     * Clear the List and count discarded logs when maxSize reached
     */
    public void reset() {
        if (logEventsList.size() >= maxSize) {
            discardedLogs += maxSize;
            logEventsList.clear();
            logEventString.clear();
        }
    }

    //For tests
    public int getCurrentLogsSize(){
        return  getCurrentLogs().size();
    }

    /**
     * Get number of discarded logs from list
     */
    public long getDiscardedLogsCount() {
        return discardedLogs;
    }

    public boolean contains(String string, Level level) {
        return this.logEventsList.stream()
                .anyMatch(event -> event.toString().contains(string)
                        && event.getLevel().equals(level));
    }
}

