package seedu.address.model;

import java.util.ArrayList;

import seedu.address.model.exceptions.NoPreviousModelStateException;

/**
 * StateList class that contains the states of TaskLists
 */
public class AddressBookStateManager {
    private static final ArrayList<AddressBook> states = new ArrayList<>();
    private static int currentState = 0;

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
        printStates();
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
        printStates();
        return states.get(currentState - 1);
    }


    /**
     * Print all states and their respective task lists for debugging.
     */
    public static void printStates() {
        System.out.println("All States:");
        int i = 0;
        for (AddressBook state : states) {
            System.out.println("State " + i);
            System.out.println(state.getPersonList());
            i++;
        }
        System.out.println("Current State Pointer: " + currentState);
    }
}