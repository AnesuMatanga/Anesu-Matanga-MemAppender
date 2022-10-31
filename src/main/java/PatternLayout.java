import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.Formatter;

public class PatternLayout extends Layout {
    static String DEFAULT_CONVERSION_PATTERN = "%p [%t]: %m%n";
    private static String category;
    private static String date;
    private static String message;
    private static String priority;
    private static String thread;
    private static String lineSeparator;
    private LoggingEvent loggingEvent;

    private static PatternLayout patternLayout;

    //CONSTRUCTORS
    public PatternLayout(){
    }

    public PatternLayout(String pattern){

    }


    @Override
    public void activateOptions() {

    }

    @Override
    public String format(LoggingEvent loggingEvent) {
       /* return  loggingEvent.getLevel() + " [" +
                loggingEvent.getThreadName() + "] " + loggingEvent.getMessage() + "\n";*/
        return formatPattern(DEFAULT_CONVERSION_PATTERN, loggingEvent);
    }

    public String formatPattern(String pattern, LoggingEvent loggingEvent){
        String patternStr = pattern;
        String formattedStr = "";
        for (int i = 0; i < patternStr.length(); i++){
            if(patternStr.charAt(i) == 'c'){
                category = loggingEvent.getLoggerName();
                formattedStr += category;
            }
            if(patternStr.charAt(i) == 'm'){
                message = loggingEvent.getMessage().toString();
                formattedStr += message;
            }
            if(patternStr.charAt(i) == 'p'){
                priority = loggingEvent.getLevel().toString();
                formattedStr += priority;
            }
            if(patternStr.charAt(i) == 't'){
                thread = loggingEvent.getThreadName();
                formattedStr += thread;
            }
            if(patternStr.charAt(i) == 'n'){
                lineSeparator = "\n";
                formattedStr += lineSeparator;
            }
            if(patternStr.charAt(i) == 'd'){
                date = loggingEvent.getLocationInformation().toString();
                formattedStr += date;
            }
            if(patternStr.charAt(i) == ' '){
                formattedStr += ' ';
            }
            if(patternStr.charAt(i) == '['){
                formattedStr += '[';
            }
            if(patternStr.charAt(i) == ']'){
                formattedStr += ']';
            }
            if(patternStr.charAt(i) == ':'){
                formattedStr += ':';
            }
        }
        return formattedStr;
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }
}
