package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, T startValue, T endValue) {
        return value.compareTo(startValue) >= 0 && value.compareTo(endValue) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static String DateToString(LocalDate ld) {
        return ld == null ? "" : ld.format(DATE_FORMATTER);
    }
    public static String TimeToString(LocalTime lt) {
        return lt == null ? "" : lt.format(TIME_FORMATTER);
    }
}

