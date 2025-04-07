package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;

public class ReminderTest {

    private static final Name VALID_NAME_MATH_EXAM = new Name("John Doe");
    private static final String VALID_EVENT_MATH_EXAM = "Final Semester Math Exam";
    private static final LocalDate VALID_DATE_MATH_EXAM = LocalDate.of(2025, 5, 10);
    private static final LocalTime VALID_TIME_MATH_EXAM = LocalTime.of(9, 0);

    private static final Name VALID_NAME_SCIENCE_TEST = new Name("Jane Smith");
    private static final String VALID_EVENT_SCIENCE_TEST = "Chapter 5 and 6 Science Test";
    private static final LocalDate VALID_DATE_SCIENCE_TEST = LocalDate.of(2025, 5, 15);
    private static final LocalTime VALID_TIME_SCIENCE_TEST = LocalTime.of(14, 30);

    private static final Reminder MATH_EXAM_REMINDER = new Reminder(VALID_NAME_MATH_EXAM, VALID_EVENT_MATH_EXAM,
            VALID_DATE_MATH_EXAM, VALID_TIME_MATH_EXAM);
    private static final Reminder SCIENCE_TEST_REMINDER = new Reminder(VALID_NAME_SCIENCE_TEST,
            VALID_EVENT_SCIENCE_TEST, VALID_DATE_SCIENCE_TEST, VALID_TIME_SCIENCE_TEST);

    @Test
    public void equals_sameObject_returnsTrue() {
        // same object -> returns true
        assertEquals(MATH_EXAM_REMINDER, MATH_EXAM_REMINDER);
    }

    @Test
    public void equals_null_returnsFalse() {
        // null -> returns false
        assertNotEquals(null, MATH_EXAM_REMINDER);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        // different type -> returns false
        assertNotEquals(MATH_EXAM_REMINDER, "Not a Reminder");
    }

    @Test
    public void equals_differentAttributes_returnsFalse() {
        // different name -> returns false
        Reminder editedReminder = new Reminder(VALID_NAME_SCIENCE_TEST, VALID_EVENT_MATH_EXAM,
                VALID_DATE_MATH_EXAM, VALID_TIME_MATH_EXAM);
        assertNotEquals(MATH_EXAM_REMINDER, editedReminder);

        // different event -> returns false
        editedReminder = new Reminder(VALID_NAME_MATH_EXAM, VALID_EVENT_SCIENCE_TEST,
                VALID_DATE_MATH_EXAM, VALID_TIME_MATH_EXAM);
        assertNotEquals(MATH_EXAM_REMINDER, editedReminder);

        // different date -> returns false
        editedReminder = new Reminder(VALID_NAME_MATH_EXAM, VALID_EVENT_MATH_EXAM,
                VALID_DATE_SCIENCE_TEST, VALID_TIME_MATH_EXAM);
        assertNotEquals(MATH_EXAM_REMINDER, editedReminder);

        // different time -> returns false
        editedReminder = new Reminder(VALID_NAME_MATH_EXAM, VALID_EVENT_MATH_EXAM,
                VALID_DATE_MATH_EXAM, VALID_TIME_SCIENCE_TEST);
        assertNotEquals(MATH_EXAM_REMINDER, editedReminder);
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        // same attributes -> returns true
        Reminder copyReminder = new Reminder(VALID_NAME_MATH_EXAM, VALID_EVENT_MATH_EXAM,
                VALID_DATE_MATH_EXAM, VALID_TIME_MATH_EXAM);
        assertEquals(MATH_EXAM_REMINDER, copyReminder);
    }

    @Test
    public void hashCode_sameAttributes_returnsSameHashCode() {
        // same attributes -> same hashCode
        Reminder copyReminder = new Reminder(VALID_NAME_MATH_EXAM, VALID_EVENT_MATH_EXAM,
                VALID_DATE_MATH_EXAM, VALID_TIME_MATH_EXAM);
        assertEquals(MATH_EXAM_REMINDER.hashCode(), copyReminder.hashCode());
    }

    @Test
    public void hashCode_differentAttributes_returnsDifferentHashCode() {
        // different attributes -> different hashCode
        assertNotEquals(MATH_EXAM_REMINDER.hashCode(), SCIENCE_TEST_REMINDER.hashCode());
    }

    @Test
    public void toString_correctFormat() {
        String expectedString = String.format("%s - %s on %s at %s",
                MATH_EXAM_REMINDER.getName(), MATH_EXAM_REMINDER.getEvent(),
                MATH_EXAM_REMINDER.getDate(), MATH_EXAM_REMINDER.getTime());

        assertEquals(expectedString, MATH_EXAM_REMINDER.toString());
    }

    @Test
    public void getters_correctValues() {
        assertEquals(VALID_NAME_MATH_EXAM, MATH_EXAM_REMINDER.getName());
        assertEquals(VALID_EVENT_MATH_EXAM, MATH_EXAM_REMINDER.getEvent());
        assertEquals(VALID_DATE_MATH_EXAM, MATH_EXAM_REMINDER.getDate());
        assertEquals(VALID_TIME_MATH_EXAM, MATH_EXAM_REMINDER.getTime());
    }
}
