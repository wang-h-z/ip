package common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The DateTimeParser class provides utility methods for parsing and formatting date-time strings.
 * It supports multiple predefined formats and can return a formatted LocalDateTime object or a fallback string.
 */
public class DateTimeParser {
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    private static final DateTimeFormatter[] FORMATTERS = {
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"), // e.g., 2/12/2019 1800
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a") // e.g., Dec 02 2019, 6:00 pm [required]
    };

    /**
     * Parses a date-time string and returns a LocalDateTime object.
     * The input string is checked against a set of predefined formats. If a valid format is found,
     * a LocalDateTime object is returned. Otherwise, an IllegalArgumentException is thrown.
     *
     * @param dateTimeStr The string that contains the user input representing a date and time.
     * @return A LocalDateTime object parsed from the input string.
     * @throws IllegalArgumentException If the input string does not match any recognized date-time format.
     */
    public static LocalDateTime parse(String dateTimeStr) throws IllegalArgumentException {
        for (DateTimeFormatter formatter : FORMATTERS) {
            LocalDateTime result = parseWithFormatter(dateTimeStr, formatter);
            if (result != null) {
                return result;
            }
        }
        throw new IllegalArgumentException("Date-time string is not in a recognized format: " + dateTimeStr);
    }

    /**
     * Attempts to parse the given date-time string using the specified formatter.
     *
     * @param dateTimeStr The date-time string to parse.
     * @param formatter The DateTimeFormatter to use for parsing the string.
     * @return A LocalDateTime object if the parsing succeeds, or null if it fails.
     */
    private static LocalDateTime parseWithFormatter(String dateTimeStr, DateTimeFormatter formatter) {
        try {
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Formats the given LocalDateTime object into a string using a custom format.
     * If the LocalDateTime is null, the method returns the provided fallback string.
     *
     * @param dateTime The LocalDateTime object to format. Can be null.
     * @param str The fallback string to use if dateTime is null.
     * @return A formatted string representing the date and time, or the fallback string if dateTime is null.
     */
    public static String format(LocalDateTime dateTime, String str) {
        if (dateTime == null) {
            return str;
        }
        return dateTime.format(CUSTOM_FORMATTER);
    }
}
