package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.ModelStateManager;
import seedu.address.model.exceptions.NoPreviousModelStateException;

/**
 * Reverts entire model state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Reverted entire model state";
    public static final String MESSAGE_FAILURE = "No previous model state found";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        try {
            ModelStateManager.undo();
        } catch (NoPreviousModelStateException e) {
            return new CommandResult(MESSAGE_FAILURE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

