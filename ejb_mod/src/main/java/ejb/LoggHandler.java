package ejb;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggHandler {

    private static final Logger logger = Logger.getLogger(LoggHandler.class.getName());

    public static void logg(Level level, String message) {
        logger.log(level, message);
    }
}
