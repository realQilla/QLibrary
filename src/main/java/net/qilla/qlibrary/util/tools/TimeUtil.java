package net.qilla.qlibrary.util.tools;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public final class TimeUtil {

    private TimeUtil() {
    }

    /**
     * Utility method to convert milliseconds to a readable format
     * @param ms Milliseconds to count
     * @param shortForm If the final string should use letters or the full word
     * @return Returns a new string containing the formatted time
     */

    public static @NotNull String getTime(long ms, boolean shortForm) {
        if (ms < 0) ms = 0;

        final long MS_IN_SECOND = 1000;
        final long SECONDS_IN_MINUTE = 60;
        final long MINUTES_IN_HOUR = 60;
        final long HOURS_IN_DAY = 24;
        final long DAYS_IN_WEEK = 7;
        final long DAYS_IN_YEAR = 365;
        final double DAYS_IN_MONTH = 30.44;

        long seconds = ms / MS_IN_SECOND;
        long minutes = seconds / SECONDS_IN_MINUTE;
        long hours = minutes / MINUTES_IN_HOUR;
        long days = hours / HOURS_IN_DAY;
        long years = (days / DAYS_IN_YEAR);
        days %= DAYS_IN_YEAR;
        long months = (long) (days / DAYS_IN_MONTH);
        days %= (long) DAYS_IN_MONTH;
        long weeks = days / DAYS_IN_WEEK;
        days %= DAYS_IN_WEEK;
        seconds %= SECONDS_IN_MINUTE;
        minutes %= MINUTES_IN_HOUR;
        hours %= HOURS_IN_DAY;

        if (shortForm) {
            if (years > 0) return years + "y";
            if (months > 0) return months + "mo";
            if (weeks > 0) return weeks + "w";
            if (days > 0) return days + "d";
            if (hours > 0) return hours + "h";
            if (minutes > 0) return minutes + "m";
            return seconds + "s";
        }

        StringBuilder result = new StringBuilder();
        appendTime(result, years, "year");
        appendTime(result, months, "month");
        appendTime(result, weeks, "week");
        appendTime(result, days, "day");
        appendTime(result, hours, "hour");
        appendTime(result, minutes, "minute");
        appendTime(result, seconds, "second");

        return result.toString().trim();
    }

    private static void appendTime(StringBuilder builder, long value, String unit) {
        if (value > 0) {
            builder.append(value).append(" ").append(unit);
            if (value > 1) builder.append("s");
            builder.append(" ");
        }
    }

    /**
     * Utility method to convert a number and a character to a number.
     * @param string The string to format
     * @return Returns the resulting number or 0 if invalid
     */

    public static long stringToMillis(@NotNull String string) {
        Preconditions.checkNotNull(string, "String cannot be null.");
        if(string.isEmpty()) return 0;

        char timeType = string.charAt(string.length() - 1);
        if(!Character.isLetter(timeType)) return Long.parseLong(string);
        long value = Long.parseLong(string.substring(0, string.length() - 1));

        return switch(timeType) {
            case 's' -> value * 1000;
            case 'm' -> value * 60 * 1000;
            case 'h' -> value * 60 * 60 * 1000;
            case 'd' -> value * 24 * 60 * 60 * 1000;
            case 'w' -> value * 7 * 24 * 60 * 60 * 1000;
            case 'y' -> value * 365 * 24 * 60 * 60 * 1000;
            default -> value;
        };
    }

    /**
     * Returns a formatted string to show the amount of time since
     * a specified time
     * @param origin The original time
     * @param shortForm If the format should use the full word, or a simple letter
     * @return Returns a formatted string
     */

    public static String timeSince(long origin, boolean shortForm) {
        long timeDifference = System.currentTimeMillis() - origin;

        return timeDifference <= 1000 ? "now" : getTime(timeDifference, shortForm) + " ago";
    }
}