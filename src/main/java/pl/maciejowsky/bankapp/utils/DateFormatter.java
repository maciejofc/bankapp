package pl.maciejowsky.bankapp.utils;


import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String timestampToString(Timestamp timestamp) {
        Instant instant = timestamp.toInstant();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
                .withZone(ZoneId.systemDefault());
        return formatter.format(instant);

    }

    public static Instant stringToInstant(String date) {
        return LocalDateTime.parse(date,
                        DateTimeFormatter.ofPattern(DATE_PATTERN))
                .atZone(ZoneId.systemDefault()).toInstant();

    }
//    public static String stringToTimestamp(String string) {
//        return timestamp.toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDateTime().toString().replace("T", " ");
//
//    }
}
