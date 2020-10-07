package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateFormatter {
    private DateFormatter() {}
    private static final DateTimeFormatter formatterT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String formatLocalDateTimeT(LocalDateTime ldt) {
        return formatterT.format(ldt);
    }
    public static String formatLocalDateTime(LocalDateTime ldt) {
        return formatter.format(ldt);
    }
}
