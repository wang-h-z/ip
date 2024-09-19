package common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeParser {
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    private static final DateTimeFormatter[] FORMATTERS = {
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),       // e.g., 2/12/2019 1800
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a"), //e.g., Dec 02 2019, 6:00 pm [required]
    };

    /**
     * Returns a LocalDateTime object based on whether the input dateTimeStr matches one of the given formats. If the
     * format is valid, a LocalDateTime object is returned. If not, an IllegalArgumentException is thrown.
     *
     * @param dateTimeStr The string that contains the user input.
     * @return
     */
    public static LocalDateTime parse(String dateTimeStr) throws IllegalArgumentException {
        if (dateTimeStr == null) {
            throw new IllegalArgumentException("Input date-time string cannot be null");
        }
        for (DateTimeFormatter formatter : FORMATTERS) {
            LocalDateTime result = parseWithFormatter(dateTimeStr, formatter);
            if (result != null) {
                return result;
            }
        }

        throw new IllegalArgumentException("Date-time string is not in a recognized format: " + dateTimeStr);
    }

    private static LocalDateTime parseWithFormatter(String dateTimeStr, DateTimeFormatter formatter) {
        try {
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Returns a String which represents the date and time in the format specified by a CUSTOM_FORMATTER.
     *
     * @param dateTime The optional LocalDateTime in the Task object.
     * @param str The mandatory String describing a Task's deadline.
     *
     * @return A String representing the deadline of a Task.
     */
    public static String format(LocalDateTime dateTime, String str) {
        if (dateTime == null) {
            return str;
        }
        return dateTime.format(CUSTOM_FORMATTER);
    }
}