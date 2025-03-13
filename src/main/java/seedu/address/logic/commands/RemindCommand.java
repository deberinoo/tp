package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
public class RemindCommand extends Command{

    public static final String COMMAND_WORD = "remind";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets a reminder for an event at a specific time and date.\n"
            + "Parameters: n/NAME e/EVENT d/DATE(YYYY-MM-DD) (must be a future date)) "
            + "t/TIME(HH:MM)\n"
            + "Example: " + COMMAND_WORD + " n/John Doe e/Math exam d/2025-06-20 t/09:00";



    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, Event: %2$s, Date: %3$s, Time: %4$s";

    private final String name;
    private final String event;
    private final String date;
    private final String time;
    //time and date will be converted into an object next time

    public RemindCommand(String name, String event, String date, String time) {
        requireAllNonNull(name, event, date, time);

        this.name = name;
        this.event = event;
        this.date = date;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, name, event, date, time));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemindCommand)) {
            return false;
        }

        // state check
        RemindCommand e = (RemindCommand) other;
        return name.equals(e.name)
                && event.equals(e.event)
                && date.equals(e.date)
                && time.equals(e.time);
    }
}
