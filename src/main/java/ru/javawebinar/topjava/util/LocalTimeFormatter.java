package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return LocalTime.parse(text, DateTimeFormatter.ISO_TIME);
    }

    @Override
    public String print(LocalTime time, Locale locale) {
        return time.format(DateTimeFormatter.ISO_TIME);
    }
}
