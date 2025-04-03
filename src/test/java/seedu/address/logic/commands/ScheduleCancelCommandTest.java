package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedule.ScheduleCancelCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.schedule.Session;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ScheduleCancelCommand.
 */
public class ScheduleCancelCommandTest {

    private Model model;
    private Model expectedModel;
    private Session session1;
    private Session session2;

    @BeforeEach
    public void setUp() {
        ReadOnlyAddressBook addressBook = new AddressBook();
        model = new ModelManager(addressBook, new UserPrefs());
        expectedModel = new ModelManager(addressBook, new UserPrefs());

        // Create two sample sessions
        session1 = new Session("Math Tutoring", "Math", LocalDate.of(2025, 3, 18),
                LocalTime.of(14, 0), Duration.ofHours(1));
        session2 = new Session("Science Tutoring", "Science", LocalDate.of(2025, 3, 19),
                LocalTime.of(15, 0), Duration.ofHours(2));

        // Add sessions to model
        model.addSession(session1);
        model.addSession(session2);
        expectedModel.addSession(session1);
        expectedModel.addSession(session2);
    }

    @Test
    public void execute_validIndex_success() {
        Index validIndex = Index.fromZeroBased(0); // Cancel first session
        ScheduleCancelCommand cancelCommand = new ScheduleCancelCommand(validIndex);

        String expectedMessage = String.format(ScheduleCancelCommand.MESSAGE_SUCCESS, session1);
        expectedModel.deleteSession(session1);

        assertCommandSuccess(cancelCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromZeroBased(5); // Out of bounds index
        ScheduleCancelCommand cancelCommand = new ScheduleCancelCommand(invalidIndex);

        assertCommandFailure(cancelCommand, model, "Invalid index! Available sessions: 1, 2 (Please use 1 to 2)");
    }

    @Test
    public void execute_noSessions_throwsCommandException() {
        model = new ModelManager(new AddressBook(), new UserPrefs()); // Empty model
        ScheduleCancelCommand cancelCommand = new ScheduleCancelCommand(Index.fromZeroBased(0));

        assertCommandFailure(cancelCommand, model, "No sessions available to cancel.");
    }
}
