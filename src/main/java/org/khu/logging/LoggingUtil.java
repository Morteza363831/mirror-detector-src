package org.khu.logging;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtil {

    private static Logger getLogger(Class<?> clazz) {

        return LoggerFactory.getLogger(clazz);

    }

    public static void error(Class<?> clazz, String method, Throwable ex) {

        Logger logger = getLogger(clazz);;

        logger.error("Class: {}, Method: {}", clazz.getSimpleName(), method, ex);

    }

    public static void debug(Class<?> clazz, String method, Object data) {

        Logger logger = getLogger(clazz);

        logger.debug("Class: {}, Method: {}, Data: {}", clazz.getSimpleName(), method, data);

    }

    public static void info(Class<?> clazz, String method, String message) {

        Logger logger = getLogger(clazz);

        logger.info("Class: {}, Method: {}, Message: {}", clazz.getSimpleName(), method, message);

    }

}
