package jp.glory.todo.test.util;

import java.util.stream.IntStream;

public class TestUtil {

    public static String repeat(final String value, final int count) {

        final StringBuilder builder = new StringBuilder();
        IntStream.range(0, count).forEach(i -> builder.append(value));

        return builder.toString();
    }
}