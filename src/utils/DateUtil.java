package utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateUtil {

    public static final String DATEFORMAT = "dd/MM/yyyy";
    public static final String TIMEFORMAT = "hh:mm:ss";
    public static final String DATETIMEFORMAT = DATEFORMAT + " " + TIMEFORMAT;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATEFORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIMEFORMAT);
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIMEFORMAT);

    public static String localDateToString(LocalDate date) {
        if (date == null) {
            return null;
        } else {
            return DATE_FORMATTER.format(date);
        }
    }

    public static LocalDate localDateFromString(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static LocalDate localDateFromDate(Date d) {
        if (d == null) return null;
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(d.getTime()), ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime localDateTimeFromDate(Date d) {
        if (d == null) return null;
        return  LocalDateTime.ofInstant(Instant.ofEpochMilli(d.getTime()), ZoneId.systemDefault());
    }

    public static Date dateFromLocalDate(LocalDate d){
        if (d == null) return null;
        return Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
