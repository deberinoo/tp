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
    public static final String MESSAGE_INVALID_INDEX = "Invalid index! Available sessions: %1$s (Please use 1 to %2$d)";

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

        if (lastShownList.isEmpty()) {
            throw new CommandException("No sessions available to cancel.");
        }

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            // Generate list of available indexes
            StringBuilder availableIndexes = new StringBuilder();
            for (int i = 0; i < lastShownList.size(); i++) {
                if (i > 0) {
                    availableIndexes.append(", ");
                }
                availableIndexes.append(i + 1); // Convert to 1-based index
            }

            throw new CommandException(String.format(
                "Invalid index! Available sessions: %s (Please use 1 to %d)",
                availableIndexes.toString(),
                lastShownList.size()
            ));
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
