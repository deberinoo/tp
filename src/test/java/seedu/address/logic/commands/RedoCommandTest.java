package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.RedoCommand.MESSAGE_FAILURE;
import static seedu.address.logic.commands.RedoCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
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
        model = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(), new UserPrefs());

        // Add some initial state to the model
        AddressBookStateManager.reset();
        model.addPerson(new PersonBuilder().withName("Alice").build());
        AddressBookStateManager.addState(model.getAddressBook().copy());
        model.addPerson(new PersonBuilder().withName("Bob").build());
        AddressBookStateManager.addState(model.getAddressBook().copy());

        // Expected model should have the same initial state
        expectedModel.addPerson(new PersonBuilder().withName("Alice").build());
        expectedModel.addPerson(new PersonBuilder().withName("Bob").build());
    }

    @Test
    public void execute_redo_success() throws Exception {
        // Simulate undo by reverting to the previous state
        AddressBookStateManager.undo();
        model.deletePerson(new PersonBuilder().withName("Bob").build());

        // Simulate redo by re-executing the previous command
        RedoCommand redoCommand = new RedoCommand();
        CommandResult result = redoCommand.execute(model);

        assertEquals(MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel.getAddressBook(), model.getAddressBook());
    }

    @Test
    public void execute_noRedoableState_failure() {
        // Reset the state manager to ensure no redoable state
        AddressBookStateManager.reset();

        // Attempt to redo when there is no redoable state
        RedoCommand redoCommand = new RedoCommand();
        CommandResult result = redoCommand.execute(model);

        assertEquals(MESSAGE_FAILURE, result.getFeedbackToUser());
    }
}
