package org.khu.logging;

import java.util.Collection;
import java.util.Map;

public final class LoggingSummaryUtil {

    public static String summarize(Object value) {

        if (value == null) {
            return "null";
        }

        if (value instanceof Collection<?> collection) {
            return String.format(
                    "%s(size=%d)",
                    collection.getClass().getSimpleName(),
                    collection.size()
            );
        }

        if (value instanceof Map<?, ?> map) {
            return String.format(
                    "%s(size=%d)",
                    map.getClass().getSimpleName(),
                    map.size()
            );
        }

        if (value.getClass().isArray()) {

            return String.format(
                    "%s(length=%d)",
                    value.getClass().getComponentType().getSimpleName(),
                    java.lang.reflect.Array.getLength(value)
            );
        }

        return value.getClass().getSimpleName();
    }

}