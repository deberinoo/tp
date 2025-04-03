package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.schedule.Session;

/**
 * Schedules a new session.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedules a session for a specific date and time. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_DATE + "2025-03-18 "
            + PREFIX_TIME + "14:00 "
            + PREFIX_DURATION + "1h30m\n\n"
            + "You can also use the following commands:\n"
            + "  schedule edit: Edits an existing scheduled session.\n"
            + "  schedule cancel: Cancels a scheduled session.";

    public static final String MESSAGE_SUCCESS = "New session scheduled: %1$s";
    public static final String MESSAGE_DUPLICATE_SESSION = "This session already exists in the schedule";

    private final Session toSchedule;

    /**
     * Creates a ScheduleCommand to schedule the specified {@code Session}
     */
    public ScheduleCommand(Session session) {
        requireNonNull(session);
        toSchedule = session;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasSession(toSchedule)) {
            throw new CommandException(MESSAGE_DUPLICATE_SESSION);
        }

        model.addSession(toSchedule);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toSchedule.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCommand)) {
            return false;
        }

        ScheduleCommand otherScheduleCommand = (ScheduleCommand) other;
        return toSchedule.equals(otherScheduleCommand.toSchedule);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toSchedule", toSchedule)
                .toString();
    }
}
