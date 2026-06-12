package org.khu.logging;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LoggingInputSummaryUtil {

    public static String summarize(String[] paramNames, Object[] args) {

        return IntStream
                .range(0, args.length)
                .mapToObj(i ->
                    paramNames[i]+ "=" + LoggingSummaryUtil.summarize(args[i])
                )
                .collect(Collectors.joining(", "));

    }

}
