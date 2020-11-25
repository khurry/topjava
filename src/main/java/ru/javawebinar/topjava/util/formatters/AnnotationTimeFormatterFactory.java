package ru.javawebinar.topjava.util.formatters;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class AnnotationTimeFormatterFactory implements AnnotationFormatterFactory<TimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return Set.of(LocalTime.class, String.class);
    }

    @Override
    public Printer<?> getPrinter(TimeFormat annotation, Class<?> fieldType) {
        return new TimeFormatter();
    }

    @Override
    public Parser<?> getParser(TimeFormat annotation, Class<?> fieldType) {
        return new TimeFormatter();
    }
}
