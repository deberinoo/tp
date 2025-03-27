package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a tutoring session in the schedule.
 */
public class Session {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private final String studentName;
    private final String subject;
    private final LocalDate date;
    private final LocalTime time;
    private final Duration duration;

    /**
     * Constructs a Schedule object.
     */
    public Session(String studentName, String subject, LocalDate date, LocalTime time, Duration duration) {
        requireNonNull(studentName);
        requireNonNull(subject);
        requireNonNull(date);
        requireNonNull(time);
        requireNonNull(duration);

        this.studentName = studentName;
        this.subject = subject;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time); // Combine date and time into LocalDateTime
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(studentName, session.studentName)
                && Objects.equals(subject, session.subject)
                && Objects.equals(date, session.date)
                && Objects.equals(time, session.time)
                && Objects.equals(duration, session.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentName, subject, date, time, duration);
    }

    @Override
    public String toString() {
        return String.format("%s - %s on %s at %s for %dh%02dm",
                studentName, subject, date.format(DATE_FORMAT),
                time.format(TIME_FORMAT),
                duration.toHours(), duration.toMinutesPart());
    }

    /**
     * Formats the duration for display
     * @return formatted duration
     */
    public String formatDurationForDisplay() {
        long hours = duration.toHours(); // Get total hours
        long minutes = duration.toMinutes() % 60; // Get remaining minutes (after hours)

        // Construct formatted string without PT prefix
        StringBuilder formattedDuration = new StringBuilder();

        if (hours > 0) {
            formattedDuration.append(hours).append("h");
        }

        if (minutes > 0) {
            formattedDuration.append(minutes).append("m");
        }

        // If the duration is zero
        if (formattedDuration.length() == 0) {
            formattedDuration.append("0m");
        }

        return formattedDuration.toString();
    }
}
