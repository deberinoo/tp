package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.schedule.ScheduleCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager; // Assuming this class exists
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.schedule.Session;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ScheduleCommand.
 */
public class ScheduleCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        // Creating mock or dummy AddressBook to pass to ModelManager constructor
        ReadOnlyAddressBook addressBook = new AddressBook(); // Replace with your actual class

        // Now create ModelManager with required dependencies
        model = new ModelManager(addressBook, new UserPrefs());
        expectedModel = new ModelManager(addressBook, new UserPrefs());
    }

    @Test
    public void execute_newSession_success() {
        // Creating a valid session using LocalDate, LocalTime, and Duration
        Session validSession = new Session("Math Tutoring", "Math", LocalDate.of(2025, 3, 18),
                                           LocalTime.of(14, 0), Duration.ofHours(1).plusMinutes(30));

        // Create the command with the valid session
        ScheduleCommand scheduleCommand = new ScheduleCommand(validSession);

        // The expected success message for adding the session
        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SUCCESS, validSession);

        // Add the session to the expected model
        expectedModel.addSession(validSession);

        // Verifying the command success
        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateSession_throwsCommandException() {
        // Creating a duplicate session
        Session duplicateSession = new Session("Math Tutoring", "Math", LocalDate.of(2025, 3, 18),
                                               LocalTime.of(14, 0), Duration.ofHours(1).plusMinutes(30));

        // Add the session to the model first
        model.addSession(duplicateSession);

        // Create the command for the duplicate session
        ScheduleCommand scheduleCommand = new ScheduleCommand(duplicateSession);

        // Verifying if it throws CommandException due to duplicate session
        assertCommandFailure(scheduleCommand, model, ScheduleCommand.MESSAGE_DUPLICATE_SESSION);
    }
}
