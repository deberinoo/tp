package seedu.address.model;

import java.util.ArrayList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exceptions.NoPreviousModelStateException;

/**
 * StateList class that contains the states of TaskLists
 */
public class AddressBookStateManager {
    private static final ArrayList<AddressBook> states = new ArrayList<>();
    private static int currentState = 0;
    private static Command previousCommand = null;

    /**
     * Instantiate task list with old task list data loaded in.
     */
    public AddressBookStateManager() {
    }

    /**
     * Add a new state and move current state number forward by one.
     */
    public static void addState(AddressBook state) {
        while (states.size() > currentState) {
            states.remove(states.size() - 1);
        }

        AddressBookStateManager.states.add(state);
        AddressBookStateManager.currentState++;
    }

    /**
     * Move current state number back by one
     */
    public static void undo() throws NoPreviousModelStateException {
        if (currentState > 1) {
            currentState--;
        } else {
            throw new NoPreviousModelStateException();
        }
    }

    /**
     * Get the current state.
     */
    public static AddressBook getCurrentState() {
        return states.get(currentState - 1);
    }

    /**
     * Get the previous command.
     */
    public static Command getPreviousCommand() throws CommandException {
        if (previousCommand != null) {
            return previousCommand;
        } else {
            throw new CommandException("No previous command found");
        }
    }

    /**
     * Set the previous command.
     */
    public static void setPreviousCommand(Command command) {
        previousCommand = command;
    }
}