package ua.edu.sumdu.j2se.Klymenko.tasks.additions;

import java.util.concurrent.TimeUnit;

/**
 * Class for good conversion of seconds into days, hours, minutes and seconds
 */
public class TimeConvetrer {

    /**
     * Returns time interval (days, hours, minutes and seconds) as a string
     * Examples:
     * Input : 3600
     * Output : 0 days 1 hours 0 minutes 0 seconds
     *
     * @param sec an integer value in seconds.
     * @return a string value that is a formatted value
     */
    public static String convertSec(int sec) {
        String str = Integer.toString(sec);
        long secondsIn = Long.parseLong(str);
        long dayCount = TimeUnit.SECONDS.toDays(secondsIn);
        long secondsCount = secondsIn - TimeUnit.DAYS.toSeconds(dayCount);
        long hourCount = TimeUnit.SECONDS.toHours(secondsCount);
        secondsCount -= TimeUnit.HOURS.toSeconds(hourCount);
        long minutesCount = TimeUnit.SECONDS.toMinutes(secondsCount);
        secondsCount -= TimeUnit.MINUTES.toSeconds(minutesCount);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d %s, ", dayCount, (dayCount == 1) ? "day"
                : "days"));
        sb.append(String.format("%d %s, ", hourCount, (hourCount == 1) ? "hour"
                : "hours"));
        sb.append(String.format("%d %s and ", minutesCount,
                (minutesCount == 1) ? "minute" : "minutes"));
        sb.append(String.format("%d %s", secondsCount,
                (secondsCount == 1) ? "second" : "seconds"));
        return sb.toString();
    }
}
