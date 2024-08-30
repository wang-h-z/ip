package common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeParser {
    // Define the possible date-time formats
    private static final DateTimeFormatter[] FORMATTERS = {
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),       // e.g., 2/12/2019 1800
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),    // e.g., 2019-12-02 18:00
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),    // e.g., 02-12-2019 18:00
            DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"),  // e.g., 12/02/2019 06:00 PM
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),  // e.g., 2019/12/02 18:00:00
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a"),//e.g., Dec 02 2019, 6:00 pm [required as this is what the toString() will be saved as]

};

    // Define the custom format for output
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public static LocalDateTime parse(String dateTimeStr) {
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDateTime.parse(dateTimeStr, formatter);  // Try to parse as LocalDateTime
            } catch (DateTimeParseException e) {
                // Continue to the next format if parsing fails
            }
        }
        // If no format matches, throw an exception
        throw new IllegalArgumentException("Date-time string is not in a recognized format: " + dateTimeStr);
    }


    public static String format(LocalDateTime dateTime, String str) {
        if (dateTime == null) {
            return str;
        }
        return dateTime.format(CUSTOM_FORMATTER);
    }
}