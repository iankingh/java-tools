package com.ian.tools.date;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @deprecated Prefer {@code java.time} and {@link io.github.iankingh.javatools.time.DateTimes}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public class DateFormatUtil {
    /**
     * @deprecated Prefer an explicit {@code DateTimeFormatter}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public String DateFormatChange(Date date) {
        return DateFormat.getDateTimeInstance().format(Objects.requireNonNull(date, "date"));
    }
}
