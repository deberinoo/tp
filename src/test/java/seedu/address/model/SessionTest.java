package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.schedule.Session;

public class SessionTest {

    private static final String VALID_STUDENT_NAME_MATH = "John Doe";
    private static final String VALID_SUBJECT_MATH = "Math";
    private static final LocalDate VALID_DATE_MATH = LocalDate.of(2025, 3, 18);
    private static final LocalTime VALID_TIME_MATH = LocalTime.of(14, 0);
    private static final Duration VALID_DURATION_MATH = Duration.ofHours(1).plusMinutes(30);

    private static final String VALID_STUDENT_NAME_SCIENCE = "Jane Smith";
    private static final String VALID_SUBJECT_SCIENCE = "Science";
    private static final LocalDate VALID_DATE_SCIENCE = LocalDate.of(2025, 3, 20);
    private static final LocalTime VALID_TIME_SCIENCE = LocalTime.of(16, 0);
    private static final Duration VALID_DURATION_SCIENCE = Duration.ofHours(2);

    private static final Session MATH_SESSION = new Session(
            VALID_STUDENT_NAME_MATH, VALID_SUBJECT_MATH, VALID_DATE_MATH, VALID_TIME_MATH, VALID_DURATION_MATH);
    private static final Session SCIENCE_SESSION = new Session(
            VALID_STUDENT_NAME_SCIENCE, VALID_SUBJECT_SCIENCE,
            VALID_DATE_SCIENCE, VALID_TIME_SCIENCE, VALID_DURATION_SCIENCE);

    @Test
    public void equals_sameObject_returnsTrue() {
        assertEquals(MATH_SESSION, MATH_SESSION);
    }

    @Test
    public void equals_null_returnsFalse() {
        assertNotEquals(null, MATH_SESSION);
    }

    @Test
    public void equals_differentSessions_returnsFalse() {
        assertNotEquals(MATH_SESSION, SCIENCE_SESSION);
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        Session anotherMathSession = new Session(
                VALID_STUDENT_NAME_MATH, VALID_SUBJECT_MATH, VALID_DATE_MATH, VALID_TIME_MATH, VALID_DURATION_MATH);
        assertEquals(MATH_SESSION, anotherMathSession);
    }

    @Test
    public void hashCode_sameAttributes_returnsSameHashCode() {
        Session anotherMathSession = new Session(
                VALID_STUDENT_NAME_MATH, VALID_SUBJECT_MATH, VALID_DATE_MATH, VALID_TIME_MATH, VALID_DURATION_MATH);
        assertEquals(MATH_SESSION.hashCode(), anotherMathSession.hashCode());
    }

    @Test
    public void formatDurationForDisplay_correctFormat() {
        // Testing the formatted duration for the Math session
        assertEquals("1h30m", MATH_SESSION.formatDurationForDisplay());

        // Testing the formatted duration for the Science session
        assertEquals("2h", SCIENCE_SESSION.formatDurationForDisplay());
    }

    @Test
    public void toString_correctFormat() {
        // Update the expected string to include student names
        String expectedMathString = "John Doe - Math on 2025-03-18 at 14:00 for 1h30m";
        String expectedScienceString = "Jane Smith - Science on 2025-03-20 at 16:00 for 2h00m";

        // Test to ensure the toString() method formats correctly
        assertEquals(expectedMathString, MATH_SESSION.toString());
        assertEquals(expectedScienceString, SCIENCE_SESSION.toString());
    }
}
