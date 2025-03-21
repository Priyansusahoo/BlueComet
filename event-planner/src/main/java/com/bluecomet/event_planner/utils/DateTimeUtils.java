package com.bluecomet.event_planner.utils;

import java.time.LocalDateTime;

/**
 * Utility Class
 * TODO: add utility class definition, add custom validation annotation, call this method
 *
 * @author Nayak, S.
 */

public class DateTimeUtils {
    private DateTimeUtils() {}

    /**
     * Validates that the start date is before the end date.
     *
     * @return boolean
     */
    public static boolean validateStartAndEndDT(LocalDateTime start, LocalDateTime end) {
        return start.isBefore(end);
    }

}
