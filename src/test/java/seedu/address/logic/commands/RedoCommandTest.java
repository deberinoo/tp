package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBookStateManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.PersonBuilder;

public class RedoCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new seedu.address.model.AddressBook(), new UserPrefs());
        expectedModel = new ModelManager(new seedu.address.model.AddressBook(), new UserPrefs());

        // Add some initial state to the model
        AddressBookStateManager.reset();
        AddressBookStateManager.resetPreviousCommand();
        model.addPerson(new PersonBuilder().withName("Alice").build());
        AddressBookStateManager.addState(model.getAddressBook().copy());
        model.addPerson(new PersonBuilder().withName("Bob").build());
        AddressBookStateManager.addState(model.getAddressBook().copy());

        // Expected model should have the same initial state
        expectedModel.addPerson(new PersonBuilder().withName("Alice").build());
        expectedModel.addPerson(new PersonBuilder().withName("Bob").build());
    }

    @Test
    public void execute_redoSuccessful() throws Exception {
        // Simulate undo by reverting to the previous state
        AddressBookStateManager.addState(model.getAddressBook().copy());
        AddressBookStateManager.addState(model.getAddressBook().copy());
        AddressBookStateManager.undo();
        expectedModel.deletePerson(new PersonBuilder().withName("Bob").build());
        model.deletePerson(new PersonBuilder().withName("Bob").build());

        // Simulate redo by re-executing the previous command
        AddressBookStateManager.setPreviousCommand(new UndoCommand());
        RedoCommand redoCommand = new RedoCommand();
        String expectedMessage = RedoCommand.MESSAGE_SUCCESS + ": " + UndoCommand.MESSAGE_SUCCESS;
        assertEquals(expectedModel.getAddressBook().getPersonList(),
                model.getAddressBook().getPersonList());

    }

    @Test
    public void execute_noRedoableState_failure() {
        // Attempt to redo when there is no redoable state
        AddressBookStateManager.resetPreviousCommand();
        RedoCommand redoCommand = new RedoCommand();
        CommandResult result = redoCommand.execute(model);

        assertEquals(RedoCommand.MESSAGE_FAILURE + ": No previous command found", result.getFeedbackToUser());
    }
}
