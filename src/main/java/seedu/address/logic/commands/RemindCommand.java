package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Reminder;
/**
 * Adding reminders to the system.
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "remind";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets a reminder for an event at a specific time and date.\n"
            + "Parameters: n/NAME e/EVENT d/DATE(YYYY-MM-DD) (must be a future date)) "
            + "t/TIME(HH:MM)\n"
            + "Example: " + COMMAND_WORD + " n/John Doe e/Math exam d/2025-06-20 t/09:00";



    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, Event: %2$s, Date: %3$s, Time: %4$s";

    private final String name;
    private final String event;
    private final LocalDate date;
    private final LocalTime time;
    /**
     * Creates a {@code RemindCommand} to add a reminder for the specified {@code Person}.
     * The reminder includes the person's name, the event description,
     * the date, and the time of the reminder.
     *
     * @param name The name of the person associated with the reminder.
     * @param event The description of the event to be reminded about.
     * @param date The date of the reminder.
     * @param time The time of the reminder.
     * @throws NullPointerException If any of the parameters are null.
     */
    public RemindCommand(String name, String event, LocalDate date, LocalTime time) {
        requireAllNonNull(name, event, date, time);

        this.name = name;
        this.event = event;
        this.date = date;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Create a new Reminder object
        Reminder reminder = new Reminder(name, event, date, time);

        // Add the reminder to the model's list
        model.addReminder(reminder);

        // Return success message
        return new CommandResult(String.format("New reminder added:\n%s", reminder));
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
