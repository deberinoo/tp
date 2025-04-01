package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Switches the panel to the specified panel.
 */
public class SwitchCommand extends Command {
    public static final String COMMAND_WORD = "switch";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the panel to the specified panel.\n"
            + "Parameters: PANEL_NAME\n"
            + "Example: " + COMMAND_WORD + " contacts" + "\n" + "Example: " + COMMAND_WORD + " reminders" + "\n"
            + "Example: " + COMMAND_WORD + " sessions";
    public static final ArrayList<String> PANELNAMES = new ArrayList<>(
            Arrays.asList("contacts", "reminders", "sessions"));
    private final String panelName;

    public SwitchCommand(String panelName) {
        this.panelName = panelName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.getMainWindow().changePanel(panelName);
        return new CommandResult("Panel changed to " + panelName + " successfully.");
    }
}
