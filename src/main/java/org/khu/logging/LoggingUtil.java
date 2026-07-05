package org.khu.logging;


import org.khu.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtil {

    private static Logger getLogger(Class<?> clazz) {

        return LoggerFactory.getLogger(clazz);

    }

    public static void error(Class<?> clazz, String method, Throwable ex) {

        if (isCustomException(ex)) {
            customExError(clazz, method, (CustomException) ex);
            return;
        }

        defaultError(clazz, method, ex);

    }

    private static void defaultError(Class<?> clazz, String method, Throwable ex) {

        Logger logger = getLogger(clazz);

        logger.error("Class: {}, Method: {}, Message: {}", clazz.getSimpleName(), method, ex.getMessage(), ex);

    }

    private static void customExError(Class<?> clazz, String method, CustomException ex) {

        Logger logger = getLogger(clazz);;

        logger.error("Class: {}, Method: {}, ErrorCode: {}, Args: {}",
                clazz.getSimpleName(),
                method,
                ex.getErrorCode(),
                java.util.Arrays.toString(ex.getArgs()),
                ex
        );
    }

    public static void debug(Class<?> clazz, String method, Object data) {

        Logger logger = getLogger(clazz);

        logger.debug("Class: {}, Method: {}, Data: {}", clazz.getSimpleName(), method, data);

    }

    public static void info(Class<?> clazz, String method, String message) {

        Logger logger = getLogger(clazz);

        logger.info("Class: {}, Method: {}, Message: {}", clazz.getSimpleName(), method, message);

    }


    private static boolean isCustomException(Throwable ex) {
        return ex instanceof CustomException;
    }

}
