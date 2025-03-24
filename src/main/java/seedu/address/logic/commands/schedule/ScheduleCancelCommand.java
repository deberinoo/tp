package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.schedule.Session;

/**
 * Cancels a scheduled session identified by its index.
 */
public class ScheduleCancelCommand extends Command {

    public static final String COMMAND_WORD = "schedule cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Cancels a scheduled session using its index.\n"
            + "Example: schedule cancel 1";

    public static final String MESSAGE_SUCCESS = "Cancelled session: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "Invalid index! Please provide a valid session number.";

    private final Index targetIndex;

    /**
     * Constructs a ScheduleCancelCommand to remove a session at the given index.
     */
    public ScheduleCancelCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Session> lastShownList = model.getSessionList().sorted(Comparator.comparing(Session::getDateTime));

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Session sessionToCancel = lastShownList.get(targetIndex.getZeroBased());
        model.deleteSession(sessionToCancel);

        return new CommandResult(String.format(MESSAGE_SUCCESS, sessionToCancel));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof ScheduleCancelCommand // Check instance type
                && targetIndex.equals(((ScheduleCancelCommand) other).targetIndex)); // Compare index
    }
}
