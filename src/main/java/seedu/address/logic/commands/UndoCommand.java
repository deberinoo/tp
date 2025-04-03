package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBookStateManager;
import seedu.address.model.Model;

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

        try {
            AddressBookStateManager.undo();
        } catch (CommandException e) {
            return new CommandResult(MESSAGE_FAILURE);
        }
        model.setAddressBook(AddressBookStateManager.getCurrentState());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

