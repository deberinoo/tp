package seedu.address.model;

import java.util.ArrayList;

import seedu.address.model.exceptions.NoPreviousModelStateException;

/**
 * StateList class that contains the states of TaskLists
 */
public class ModelStateManager {
    private static final ArrayList<Model> states = new ArrayList<>();
    private static int currentState = 0;

    /**
     * Instantiate task list with old task list data loaded in.
     */
    public ModelStateManager() {
    }

    /**
     * Add a new state and move current state number forward by one.
     */
    public static void addState(Model state) {
        while (states.size() > currentState) {
            states.remove(states.size() - 1);
        }

        ModelStateManager.states.add(state);
        ModelStateManager.currentState++;
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
    public static Model getCurrentState() {
        printStates();
        return states.get(currentState - 1);
    }


    /**
     * Print all states and their respective task lists for debugging.
     */
    public static void printStates() {
        System.out.println("All States:");
        int i = 0;
        for (Model state : states) {
            System.out.println("State " + i);
            System.out.println(state.getAddressBook().getPersonList());
            i++;
        }
        System.out.println("Current State Pointer: " + currentState);
    }
}