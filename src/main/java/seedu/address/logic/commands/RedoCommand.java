package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBookStateManager;
import seedu.address.model.Model;

/**
 * Redo previous command.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Executed previous command";
    public static final String MESSAGE_FAILURE = "Unable to execute previous command";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        String successMessage = MESSAGE_SUCCESS + ": ";
        try {
            Command previousCommand = AddressBookStateManager.getPreviousCommand();

            CommandResult previousCommandResult = previousCommand.execute(model);
            String previousCommandFeedback = previousCommandResult.getFeedbackToUser();
            successMessage += previousCommandFeedback;
        } catch (CommandException e) {
            return new CommandResult(MESSAGE_FAILURE + ": " + e.getMessage());
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(successMessage);
    }
}
