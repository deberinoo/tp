package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.schedule.ScheduleEditCommand;
import seedu.address.logic.commands.schedule.ScheduleEditCommand.EditScheduleDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.schedule.Session;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ScheduleEditCommand.
 */
public class ScheduleEditCommandTest {

    private Model model;
    private Model expectedModel;
    private Session session1;
    private Session session2;

    @BeforeEach
    public void setUp() {
        ReadOnlyAddressBook addressBook = new AddressBook();

        model = new ModelManager(addressBook, new UserPrefs());
        expectedModel = new ModelManager(addressBook, new UserPrefs());

        session1 = new Session("Alice", "Math", LocalDate.of(2025, 3, 18),
                               LocalTime.of(14, 0), Duration.ofHours(1));
        session2 = new Session("Bob", "Science", LocalDate.of(2025, 3, 19),
                               LocalTime.of(16, 0), Duration.ofHours(2));

        model.addSession(session1);
        model.addSession(session2);
        expectedModel.addSession(session1);
        expectedModel.addSession(session2);
    }

    @Test
    public void execute_editSession_success() {
        Index targetIndex = Index.fromOneBased(1); // Editing the first session
        ScheduleEditCommand.EditScheduleDescriptor descriptor = new ScheduleEditCommand.EditScheduleDescriptor();
        descriptor.setName("Alice Updated");
        descriptor.setSubject("Physics");
        descriptor.setDate(LocalDate.of(2025, 3, 20));

        ScheduleEditCommand editCommand = new ScheduleEditCommand(targetIndex, descriptor);
        Session editedSession = new Session("Alice Updated", "Physics",
                                            LocalDate.of(2025, 3, 20),
                                            session1.getTime(), session1.getDuration());

        expectedModel.updateSession(session1, editedSession);
        String expectedMessage = String.format(ScheduleEditCommand.MESSAGE_SUCCESS, editedSession);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(10); // No such session exists
        ScheduleEditCommand.EditScheduleDescriptor descriptor = new ScheduleEditCommand.EditScheduleDescriptor();
        descriptor.setName("Invalid");

        ScheduleEditCommand editCommand = new ScheduleEditCommand(outOfBoundIndex, descriptor);

        String expectedMessage = String.format(
            "Invalid index! Available sessions: 1, 2 (Please use 1 to 2)"
        );
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_noFieldsEdited_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Ensure there is at least one session before running the test
        if (model.getSessionList().isEmpty()) {
            model.addSession(new Session("Alice", "Math", LocalDate.of(2025, 3, 19),
                    LocalTime.of(14, 0), Duration.ofHours(2)));
        }

        Index validIndex = Index.fromZeroBased(0);
        EditScheduleDescriptor emptyDescriptor = new EditScheduleDescriptor();
        ScheduleEditCommand editCommand = new ScheduleEditCommand(validIndex, emptyDescriptor);

        assertThrows(CommandException.class, () -> editCommand.execute(model),
            ScheduleEditCommand.MESSAGE_NOT_EDITED);
    }
}
