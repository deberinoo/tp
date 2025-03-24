package seedu.address.model;

import java.util.ArrayList;

/**
 * StateList class that contains the states of TaskLists
 */
public class StateManager {
    private static final ArrayList<Model> states = new ArrayList<>();
    private static int currentState = 0;

    /**
     * Instantiate task list with old task list data loaded in.
     */
    public StateList() {
    }

    /**
     * Add a new state and move current state number forward by one.
     */
    public static void addState(AddressBook state) {
        while (states.size() > currentState) {
            states.remove(states.size() - 1);
        }

        StateList.states.add(new TaskList(state));
        StateList.currentState++;
    }

    /**
     * Move current state number back by one
     */
    public static void undo() {
        if (currentState > 1) {
            currentState--;
        }
    }

    /**
     * Get the current state.
     */
    public static AddressBook getCurrentState() {
        return states.get(currentState - 1);
    }


//    /**
//     * Print all states and their respective task lists for debugging.
//     */
//    public static void printStates() {
//        System.out.println("All States:");
//        for (AddressBook state : states) {
//            for (int j = 0; j < state.getSize(); j++) {
//                System.out.println("Task " + j + ": " + state.getTask(j + 1));
//            }
//        }
//        System.out.println("Current State Pointer: " + currentState);
//    }
}