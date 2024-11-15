package global.kajisaab.common.utils;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DateUtils {
    public DateUtils() {
    }

    public static LocalDateTime getCurrentUtcDateTime() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now(ZoneOffset.UTC);
    }

    public static Date getDateAfterMinute(int minutes) {
        return Date.from(LocalDateTime.now().plusMinutes((long)minutes).toInstant(ZoneOffset.UTC));
    }

    public static Instant getDateTimeAfterDays(int days){
        return LocalDateTime.now().plusDays((long)days).toInstant(ZoneOffset.UTC);
    }

    public static boolean isBeforeNow(Long seconds) {
        return getCurrentUtcDateTime().isAfter(LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC));
    }

    public static boolean isAfterNow(Long seconds) {
        return getCurrentUtcDateTime().isBefore(LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC));
    }

    public static Date convertToUtcDate(String fieldValue) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(fieldValue, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            ZonedDateTime utcDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
            return Date.from(utcDateTime.toInstant());
        } catch (DateTimeParseException var3) {
            return null;
        }
    }

    public static LocalDateTime startOfTheWeek() {
        return getCurrentDate().minusDays((long)(getCurrentDate().getDayOfWeek().getValue() % 7)).atStartOfDay();
    }

    public static LocalDateTime startOfTheMonth() {
        return getCurrentDate().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
    }

    public static LocalDateTime startOfPreviousDay() {
        return LocalDateTime.of(getCurrentDate().minusDays(1L), LocalTime.MIN);
    }

    public static LocalDateTime endOfPreviousDay() {
        return LocalDateTime.of(getCurrentDate().minusDays(1L), LocalTime.MAX);
    }

    public static LocalDateTime getStartOfPreviousWeek() {
        return LocalDateTime.of(getCurrentDate().minusWeeks(1L).minusDays((long)(getCurrentDate().getDayOfWeek().getValue() % 7)), LocalTime.MIN);
    }

    public static LocalDateTime getEndOfPreviousWeek() {
        return LocalDateTime.of(getCurrentDate().minusWeeks(1L).minusDays((long)(getCurrentDate().getDayOfWeek().getValue() % 7)).plusDays(6L), LocalTime.MAX);
    }

    public static LocalDateTime getStartOfPreviousMonth() {
        return LocalDateTime.of(getCurrentDate().minusMonths(1L).with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    public static LocalDateTime getEndOfPreviousMonth() {
        return LocalDateTime.of(getCurrentDate().minusMonths(1L).with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }

    public static LocalDateTime getStartOfCurrentYear() {
        return getCurrentDate().with(TemporalAdjusters.firstDayOfYear()).atStartOfDay();
    }
}