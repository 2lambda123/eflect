package si.um.feri.lpm.green.server;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

class GreenFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return record.getLevel() + ": " +
                formatMessage(record) +
                System.lineSeparator();
    }
}

public class GreenLogger {
    private static Logger logger = null;

    public synchronized static Logger get() {
        if (logger == null) {
            logger = Logger.getLogger(GreenLogger.class.getName());
            logger.setUseParentHandlers(false);
            ConsoleHandler handler = new ConsoleHandler();
            handler.setFormatter(new GreenFormatter());
            logger.addHandler(handler);
        }
        return logger;
    }
}
