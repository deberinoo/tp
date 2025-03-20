package seedu.address.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents a Reminder in the application.
 */
public class Reminder {
    private final String name;
    private final String event;
    private final LocalDate date;
    private final LocalTime time;

    /**
     * Represents a reminder in the reminder system.
     * A reminder contains information about an event or task, including
     * the associated person, event description, date, and time.
     *
     * <p>This class provides methods to access and modify reminder details,
     * as well as utility methods for comparing and displaying reminders.</p>
     *
     * <p>Reminders are immutable; once created, their core attributes cannot be changed.
     * If modifications are needed, a new Reminder object should be created.</p>
     */
    public Reminder(String name, String event, LocalDate date, LocalTime time) {
        this.name = name;
        this.event = event;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getEvent() {
        return event;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Reminder)) {
            return false;
        }
        Reminder otherReminder = (Reminder) other;
        return name.equals(otherReminder.name)
                && event.equals(otherReminder.event)
                && date.equals(otherReminder.date)
                && time.equals(otherReminder.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, event, date, time);
    }

    @Override
    public String toString() {
        return String.format("Reminder: %s - %s on %s at %s", name, event, date, time);
    }
}
