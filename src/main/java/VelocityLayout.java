import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;

public class VelocityLayout extends Layout {
    private static String category;
    private static String date;
    private static String message;
    private static String priority;
    private static String thread;
    private static String lineSeparator;
    private static String CONVERSION_PATTERN = "[$t]: $m$n";
    private LoggingEvent loggingEvent;

    private static VelocityLayout velocityLayout;

    public VelocityLayout() {
    }

    public VelocityLayout(String pattern) {
        setConversionPattern(pattern);
    }

    @Override
    public void activateOptions() {

    }

    @Override
    public String format(LoggingEvent loggingEvent) {
        String patternStr = CONVERSION_PATTERN;
        String formattedStr = "";
        String newFormatted = "";

        for (int i = 0; i < patternStr.length(); i++) {
            /*first, get and initialize an engine */
            VelocityEngine ve = new VelocityEngine();
            ve.init();
            /*get the template*/
            Template template = ve.getTemplate(
                    "velocityTemp.vm");
            /*Create a context and add data*/
            VelocityContext context = new VelocityContext();
            context.put("pattern", patternStr.charAt(i));
            context.put("strSize", patternStr.length());
            context.put("loggingEvent", loggingEvent);
            context.put("message", loggingEvent.getMessage().toString());
            context.put("thread", loggingEvent.getThreadName());
            context.put("priority", loggingEvent.getLevel());
            context.put("category", loggingEvent.getLoggerName());
            context.put("lineSeparator", "\n");

            /*Now rendering template into a StringWriter */
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            formattedStr += writer.toString();
            //System.out.print(formattedStr);
        }
        //System.out.print(formattedStr);
        return formattedStr;
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    public static void setConversionPattern(String conversionPattern) {
        CONVERSION_PATTERN = conversionPattern;
    }

    public String getConversionPattern() {
        return CONVERSION_PATTERN;
    }

    public void setCategory(String cat) {
        category = cat;
    }

    public void setMessage(String msg) {
        message = msg;
    }

    public void setPriority(String pri) {
        priority = pri;
    }

    public void setThread(String thre) {
        thread = thre;
    }

    public void setLineSeparator(String lineSep) {
        lineSeparator = lineSep;
    }


    public String getFormatted(String formattedStr,
                               LoggingEvent loggingEvent,
                               StringWriter writer) {
        System.out.println("in GF");
        String variable = writer.toString();
        System.out.println("IN : " + variable);
        if (variable == "m") {
            System.out.println("in m");
            formattedStr += loggingEvent.getMessage();
        }
        if (writer.toString() == "c") {
            formattedStr += loggingEvent.getLoggerName();
        }
        if (variable == "t") {
            formattedStr += loggingEvent.getThreadName();
        }
        if (writer.toString() == "p") {
            formattedStr += loggingEvent.getLevel();
        }
        if (writer.toString() == "\n") {
            formattedStr += "\n";
        }
        if (writer.toString() == " ") {
            formattedStr += " ";
        }
        if (writer.toString() == "]") {
            formattedStr += "]";
        }
        if (writer.toString() == "[") {
            formattedStr += "[";
        }
        if (writer.toString() == ":") {
            formattedStr += ":";
        }
        return  formattedStr;
    }

    public String removeLineBreaks(String str){
        String newString = str;
        for (int i = 0; i < newString.length(); i++){
            if (newString.charAt(i) == '\n' && i != newString.length() - 1){
                newString.replace('\n', ' ');
            }
        }
        return newString;
    }
}
