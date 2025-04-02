package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.AddressBookStateManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.PersonBuilder;

public class UndoCommandTest {
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
    public void execute_undoSuccessful() {
        // Simulate undo by reverting to the previous state
        AddressBookStateManager.addState(model.getAddressBook().copy());
        AddressBookStateManager.undo();
        expectedModel.deletePerson(new PersonBuilder().withName("Bob").build());
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noUndoableState_failure() {
        // Prepare at least two saved states to allow undos
        AddressBook state1 = new AddressBook(); // empty or default state
        AddressBook state2 = new AddressBook(); // another state (could be same or modified)

        AddressBookStateManager.addState(state1);
        AddressBookStateManager.addState(state2);

        // Undo back to state1
        AddressBookStateManager.undo();
        model.setAddressBook(AddressBookStateManager.getCurrentState());

        // Undo back to initial (before state1) — this should leave us at currentState = 1
        AddressBookStateManager.undo();
        model.setAddressBook(AddressBookStateManager.getCurrentState());

        AddressBookStateManager.undo();
        // Now we’ve exhausted the undo stack — next undo should fail
        UndoCommand undoCommand = new UndoCommand();
        CommandResult result = undoCommand.execute(model);

        assertEquals(UndoCommand.MESSAGE_FAILURE, result.getFeedbackToUser());
    }
}
