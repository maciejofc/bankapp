package pl.maciejowsky.bankapp.utils;

import java.sql.Timestamp;
import java.time.ZoneId;

public class DateFormatter {
    public static String timestampToString(Timestamp timestamp) {
        return timestamp.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime().toString().replace("T", " ");

    }
}
